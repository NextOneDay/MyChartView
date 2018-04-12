package com.nextoneday.chartview.kline;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.nextoneday.chartview.R;
import com.nextoneday.chartview.back.back2.bean.KLineBean;
import com.nextoneday.chartview.listener.CoupleChartGestureListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/12.
 *
 * 1.放在布局中就能够使用，
 * 2.设置数据就能够显示数据
 */

public class MyKlineChart  extends CombinedChart{


    private ArrayList<KLineBean> mDatas; // 数据集合

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
     * 初始化这个view的布局
     *
     */

    private void initView() {

        setBackgroundFrame();
        setChartGestureSet();
        setXY();
//        setEvent();

    }

    /**
     * 通过外部把数据个传进来
     * @param kLineDatas
     */
    public void setViewData(ArrayList<KLineBean> kLineDatas){
        this.mDatas=kLineDatas;
    }

    /**
     * 联级滑动, 以及点击后高亮线的显示，以及修改高亮线的样式
     */
    private void setEvent(final Chart[] charts) {
        // 将K线控的滑动事件传递给其他交易量控件
        setOnChartGestureListener(new CoupleChartGestureListener(this, charts));

        //设置高亮线点击监听
        setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // TODO: 2018/4/12 高亮线部分需要重写
                Highlight highlight = new Highlight(h.getX(), h.getY(), h.getDataSetIndex());

//                float touchY = h.getTouchY() - mChartKline.getHeight();
//                Highlight h1 = mChartVolume.getHighlightByTouchPoint(h.getXIndex(), touchY);
//                highlight.setTouchY(touchY);
//                if (null == h1) {
//                    highlight.setTouchYValue(0);
//                } else {
//                    highlight.setTouchYValue(h1.getTouchYValue());
//                }
//                mChartVolume.highlightValues(new Highlight[]{highlight});
//
//                Highlight highlight2 = new Highlight(h.getXIndex(), h.getValue(), h.getDataIndex(), h.getDataSetIndex());
//
//                float touchY2 = h.getTouchY() - mChartKline.getHeight() - mChartVolume.getHeight();
//                Highlight h2 = mChartCharts.getHighlightByTouchPoint(h.getXIndex(), touchY2);
//                highlight2.setTouchY(touchY2);
//                if (null == h2) {
//                    highlight2.setTouchYValue(0);
//                } else {
//                    highlight2.setTouchYValue(h2.getTouchYValue());
//                }
//                mChartCharts.highlightValues(new Highlight[]{highlight2});
//
//                updateText(e.getXIndex());
            }

            @Override
            public void onNothingSelected() {
                charts[0].highlightValue(null);
                charts[1].highlightValue(null);
            }
        });

    }

    /**
     * 设置X轴Y轴的显示
     */
    private void setXY() {

//        xy轴基本设置
        XAxis xAxis = getXAxis(); //x轴
        xAxis.setDrawLabels(true);  // 绘制标签

        xAxis.setDrawGridLines(true); // 网格线
        xAxis.setDrawAxisLine(true); // 轴线
        xAxis.setAxisLineWidth(3);
        xAxis.setGridLineWidth(8);

        xAxis.setGridColor(R.color.colorPrimary);
        xAxis.setAxisLineColor(R.color.colorAccent);
        xAxis.setTextColor(R.color.text_grey);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  // 设置位于底部
        xAxis.setAvoidFirstLastClipping(true); // 设置首尾的值是否自动调整，避免被遮挡

        xAxis.enableGridDashedLine(3,2,1); // 虚线
        xAxis.enableAxisLineDashedLine(3,2,1); //实线


        //Y轴
        YAxis axisLeft = getAxisLeft();
        axisLeft.setDrawLabels(true);
        axisLeft.setDrawGridLines(true);
        axisLeft.setDrawAxisLine(true);
        axisLeft.setAxisLineWidth(3);
        axisLeft.setGridLineWidth(8);

        axisLeft.setGridColor(R.color.bg_yellow);
        axisLeft.setAxisLineColor(R.color.common_bg);
        axisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        axisLeft.setTextColor(R.color.text_grey);

        axisLeft.enableAxisLineDashedLine(3,2,1);
        axisLeft.enableAxisLineDashedLine(3,3,1);

        axisLeft.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisLeft.setSpaceTop(10);//距离顶部留白


        //y轴右边
        YAxis axisRight = getAxisRight();

        axisRight.setDrawLabels(true);
        axisRight.setDrawGridLines(true);
        axisRight.setDrawAxisLine(true);
        axisRight.setAxisLineWidth(3);
        axisRight.setGridLineWidth(8);

        axisRight.setGridColor(R.color.bg_yellow);
        axisRight.setAxisLineColor(R.color.common_bg);
        axisRight.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        axisRight.setTextColor(R.color.text_grey);

        axisRight.enableAxisLineDashedLine(3,2,1);
        axisRight.enableAxisLineDashedLine(3,3,1);

        axisRight.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisRight.setSpaceTop(10);//距离顶部留白
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
