package com.nextoneday.chartview.back.back2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nextoneday.chartview.R;


public class ChartsActivity
        extends AppCompatActivity
        implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        findViewById(R.id.fenshi).setOnClickListener(this);
        findViewById(R.id.kline).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.kline:
                startActivity(new Intent(this, MyKLineChart.class));
                break;
            case R.id.fenshi:
            startActivity(new Intent(this, MyKLineChart.class));
            break;
            default:
                break;
        }
    }
}
