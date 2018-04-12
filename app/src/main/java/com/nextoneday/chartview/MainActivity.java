package com.nextoneday.chartview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nextoneday.chartview.kline.MyKlineChart;

public class MainActivity extends AppCompatActivity {

    private MyKlineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mChart = findViewById(R.id.chart);
    }
}
