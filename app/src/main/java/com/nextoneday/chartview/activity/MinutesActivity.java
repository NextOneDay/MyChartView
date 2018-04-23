package com.nextoneday.chartview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nextoneday.chartview.R;
import com.nextoneday.chartview.back.back2.bean.ConstantTest;
import com.nextoneday.chartview.back.back2.bean.DataParse;
import com.nextoneday.chartview.back.back2.bean.MinutesBean;
import com.nextoneday.chartview.view.MinutesBarChart;
import com.nextoneday.chartview.view.MinutesLineChart;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/16.
 */

public class MinutesActivity extends AppCompatActivity {

    private MinutesLineChart mMinChart;
    private DataParse mData;
    private ArrayList<MinutesBean> minData;
    private MinutesBarChart mMinutesBarChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minute);
        initView();
        initData();
        setViewData();
    }

    private void setViewData() {
        mMinChart.setViewData(minData);
        setLineChartMarkerView();
        mMinChart.setEvent();// 设置联动的chart

        mMinutesBarChart.setViewData(minData);
        setBarChartMarkerView();
        mMinutesBarChart.setEvent();
    }

    /**
     * 设置分时图的markerview ，
     */
    private void setLineChartMarkerView() {

    }
    private void setBarChartMarkerView(){

    }

    private void initData() {
             /*方便测试，加入假数据*/
        mData = new DataParse();
        JSONObject object = null;
        try {
            object = new JSONObject(ConstantTest.MINUTESURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("数据-----", object.toString());
        mData.parseMinutes(object);

        minData = mData.getDatas(); //分时数据
    }

    private void initView() {
        mMinChart = findViewById(R.id.min_chart);

        mMinutesBarChart = findViewById(R.id.min_bar_chart);
    }
}
