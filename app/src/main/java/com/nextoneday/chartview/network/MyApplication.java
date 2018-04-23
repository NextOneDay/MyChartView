package com.nextoneday.chartview.network;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2018/4/3.
 */

public class MyApplication extends Application {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        mContext=this;

    }

    public static  Context getMyApplicationContext(){
        return mContext;
    }
}
