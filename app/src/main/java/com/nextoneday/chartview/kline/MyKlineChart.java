package com.nextoneday.chartview.kline;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.nextoneday.chartview.R;

/**
 * Created by Administrator on 2018/4/12.
 *
 * 1.放在布局中就能够使用，
 * 2.设置数据就能够显示数据
 */

public class MyKlineChart  extends CombinedChart{


    public MyKlineChart(Context context) {
        super(context);
        initView();
    }


    public MyKlineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyKlineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }


    /**
     * 加载蜡烛图、ma线
     *
     */

    private void initView() {

        setBackgroundFrame();
        setChartGestureSet();
        setXY();

    }

    /**
     * 设置X轴Y轴的显示
     */
    private void setXY() {
        XAxis xAxis = getXAxis(); //x轴
        xAxis.setDrawLabels(true);  // 绘制标签

        xAxis.setDrawGridLines(true); // 网格线
        xAxis.setDrawAxisLine(true); // 轴线
        xAxis.setAxisLineWidth(3);
        xAxis.setGridLineWidth(8);

        xAxis.enableGridDashedLine(3,2,1); // 虚线
        xAxis.enableAxisLineDashedLine(3,2,1); //实线
    }

    /**
     * 图表手势基本操作
     */
    private void setChartGestureSet() {

        // 允许缩放操作
        setScaleEnabled(true);
        // 允许拖动
        setDragEnabled(true);

        //允许Y轴 缩放，因为不需要x轴的放大缩小
        setScaleYEnabled(true);

        //双击放大缩小
        setDoubleTapToZoomEnabled(true);
        //手指滑动抛掷图表后继续减速滚动
        setDragDecelerationEnabled(true);
        //减速摩擦系数
        setDragDecelerationFrictionCoef(0.8f);
    }


    /**
     * 背景、边框 虚线等
     */
    private void setBackgroundFrame() {
        //背景
        setBackgroundColor(Color.BLACK);

        //文字描述
        Description description = getDescription();
        description.setText("这是文字描述");
        description.setTextColor(R.color.text_blue);

        //没有数据时候显示
        setNoDataText("没有内容，正在加载");

        //边框
        setDrawBorders(true);
        setBorderWidth(3);
        setBorderColor(Color.WHITE);

//        内框
        setDrawGridBackground(true);

        //不绘制图例
        Legend legend = getLegend();
        legend.setEnabled(false);
    }


    private void setStockLine() {

    }

}
