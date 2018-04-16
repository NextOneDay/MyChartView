package com.nextoneday.chartview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2018/4/16.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        Button kline = findViewById(R.id.kline);
        Button minute = findViewById(R.id.fenshi);
        kline.setOnClickListener(this);
        minute.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kline:
                Intent intent = new Intent(this, KLineActivity.class);
                startActivity(intent);
                break;
            case R.id.fenshi:

                Intent minute = new Intent(this, MinutesActivity.class);
                startActivity(minute);
                break;
        }
    }
}
