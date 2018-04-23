package com.nextoneday.chartview.network;


import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/4/23.
 */

public interface APIService {

    @GET("/data")
    Observable<List<String>> getNetData(@Field("data") String data);

    /**
     * 请求成交明细
     *
     */
    @GET("/api/v1/tlk")
    Observable<List<String>>getTraderDetail(@Query("symbol") String symbol);
}
