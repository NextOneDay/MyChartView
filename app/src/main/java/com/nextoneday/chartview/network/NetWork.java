package com.nextoneday.chartview.network;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Administrator on 2018/4/23.
 */

public class NetWork {


    private OkHttpClient mOkhttp;
    private static NetWork mNetwork;

    public static NetWork getInstance() {
        if (mNetwork == null) {
            synchronized (NetWork.class) {
                if (mNetwork == null) {
                    mNetwork = new NetWork();
                }
            }
        }

        return mNetwork;
    }

    private NetWork() {
        //写个单例
        initOkhttp();
        initRetrofit();
    }

    private APIService apiService;

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstance.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mOkhttp)
                .build();
        apiService = retrofit.create(APIService.class);
    }


    public Observable<List<String>> getNewPrice(String symbol) {


        return apiService.getNetData(symbol);
    }

    public Observable<List<String>> getTraderDetail(String symbol) {
        return apiService.getTraderDetail(symbol);

    }


    public void initOkhttp() {

        if (mOkhttp == null) {
            synchronized (NetWork.class) {
                if (mOkhttp == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File("目录", "HttpCache"), 1024 * 1024 * 10);
                    mOkhttp = new OkHttpClient.Builder()
                            .cache(cache)
//                            .addInterceptor(interceptor) //添加cookie 、token拦截器
                            .sslSocketFactory(createSSLSocketFactory())
                            .addNetworkInterceptor(new CacheInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }

    /**
     * 设置https 全部信任
     * @return
     */
    private SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }


    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            // 有网络时 设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (CommonUtil.isNetworkAvailable(MyApplication.getMyApplicationContext())) {
                //有网络时只从网络获取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if (CommonUtil.isNetworkAvailable(MyApplication.getMyApplicationContext())) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }


    /**
     * 数据缓存
     */
    public class CookieInterceptor implements Interceptor {
        /*是否缓存标识*/
        private boolean cache;
        /*url*/
        private String url;

        public CookieInterceptor(boolean cache, String url) {
            this.url = url;
            this.cache = cache;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if (cache) {
                ResponseBody body = response.body();
                BufferedSource source = body.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                Charset charset = Charset.defaultCharset();
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(charset);
                }
                String bodyString = buffer.clone().readString(charset);
                CookieResulte resulte = null;
//                resulte =dbUtil.queryCookieBy(url);
                long time = System.currentTimeMillis();
            /*保存和更新本地数据*/
                if (resulte == null) {
                    resulte = new CookieResulte(url, bodyString, time);
//                    dbUtil.saveCookie(resulte);
                } else {
                    resulte.setResulte(bodyString);
                    resulte.setTime(time);
//                    dbUtil.updateCookie(resulte);
                }
            }
            return response;
        }
    }
}