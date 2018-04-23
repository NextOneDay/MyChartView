package com.nextoneday.chartview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.Chart;
import com.nextoneday.chartview.R;
import com.nextoneday.chartview.back.back2.bean.ConstantTest;
import com.nextoneday.chartview.back.back2.bean.DataParse;
import com.nextoneday.chartview.back.back2.bean.KLineBean;
import com.nextoneday.chartview.view.BandsLineChart;
import com.nextoneday.chartview.view.MyKlineChart;
import com.nextoneday.chartview.view.OtherViewChart;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KLineActivity extends AppCompatActivity {

    private MyKlineChart mChart;
    private DataParse mData;
    private ArrayList<KLineBean> kLineDatas;
    private BandsLineChart mVolume;
    private OtherViewChart mOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kline_view);
        mChart = findViewById(R.id.chart);
        mVolume = findViewById(R.id.volume);
        mOther = findViewById(R.id.other);
        initData();
        setViewData();
    }


    private void initData() {

          /*方便测试，加入假数据*/
        mData = new DataParse();
        JSONObject object = null;
        try {
            object = new JSONObject(ConstantTest.KLINEURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("数据-----", object.toString());
        mData.parseKLine(object);

        kLineDatas = mData.getKLineDatas(); //K线数据
//        mData.initLineDatas(kLineDatas); // 获取成交量


    }


    private void setViewData() {
        mChart.setViewData(kLineDatas);
        setChartMarkerView();
        mChart.setEvent(new Chart[]{mVolume,mOther});// 设置联动的chart

//        成交量
        mVolume.setViewData(kLineDatas,mData.getVolmax());
        setVolumeMarkerView();
        mVolume.setEvent(new Chart[]{mChart,mOther});

        mOther.setViewData(kLineDatas);
        setOtherMarkerView();
        mOther.setEvent(new Chart[]{mChart,mVolume});

    }

    private void setOtherMarkerView() {

    }

    /**
     * 给volume 设置markerview
     *
     */
    private void setVolumeMarkerView() {

    }


    /**
     * 给view 设置markerview的数据，
     */
    private void setChartMarkerView() {
        // TODO: 2018/4/12  给markview设置数据
    }
}
