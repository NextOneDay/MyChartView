package com.nextoneday.chartview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.Chart;
import com.nextoneday.chartview.back.back2.bean.ConstantTest;
import com.nextoneday.chartview.back.back2.bean.DataParse;
import com.nextoneday.chartview.back.back2.bean.KLineBean;
import com.nextoneday.chartview.view.MyKlineChart;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MyKlineChart mChart;
    private DataParse mData;
    private ArrayList<KLineBean> kLineDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mChart = findViewById(R.id.chart);

        initData();
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

        mChart.setViewData(kLineDatas);

        setMarkerView();
        mChart.setEvent(new Chart[]{});// 设置联动的chart
    }

    /**
     * 给view 设置markerview的数据，
     */
    private void setMarkerView() {
        // TODO: 2018/4/12  给markview设置数据
    }
}
