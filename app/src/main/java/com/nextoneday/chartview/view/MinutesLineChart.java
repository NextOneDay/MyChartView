package com.nextoneday.chartview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.nextoneday.chartview.R;
import com.nextoneday.chartview.back.back2.bean.MinutesBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/13.
 */

public class MinutesLineChart extends LineChart {
    private ArrayList<MinutesBean> mDatas;

    public MinutesLineChart(Context context) {
        super(context);
        initView();
    }

    public MinutesLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MinutesLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }


    private void initView() {

        setBackgroundFrame();
        setChartGestureSet();
        setXY();
    }

    /**
     * 背景、边框 虚线等
     */
    private void setBackgroundFrame() {
        //背景
        setBackgroundColor(getResources().getColor(R.color.minute_black));

        //文字描述
        getDescription().setEnabled(false);
//        description.setText("这是文字描述");
//        description.setTextColor(R.color.text_blue);

        //没有数据时候显示
        setNoDataText("没有内容，正在加载");
        setMaxVisibleValueCount(50);

        //边框
        setDrawBorders(true);
        setBorderWidth(1);
        setBorderColor(getResources().getColor(R.color.minute_grayLine));

        setMinOffset(0f);
        setExtraOffsets(0f, 0f, 0f, 3f);

        //不绘制图例
        Legend legend = getLegend();
        legend.setEnabled(false);
    }

    /**
     * 图表手势基本操作
     */
    private void setChartGestureSet() {

        // 允许缩放操作
        setScaleEnabled(true);
        // 允许拖动
        setDragEnabled(true);

        //允许X轴 缩放，因为不需要Y轴的放大缩小
        setScaleYEnabled(false);
        //双击放大缩小
        setDoubleTapToZoomEnabled(true);

        //设置最大值和最小值
        setAutoScaleMinMaxEnabled(true);
        //手指滑动抛掷图表后继续减速滚动
        setDragDecelerationEnabled(true);
        //减速摩擦系数
        setDragDecelerationFrictionCoef(0.8f);
    }

    /**
     * 设置X轴Y轴的显示
     */
    private void setXY() {

//        xy轴基本设置
        // TODO: 2018/4/13 这里x轴可能不需要标签
        XAxis xAxis = getXAxis(); //x轴
        xAxis.setDrawLabels(true);  // 绘制标签
        xAxis.setDrawGridLines(false); // 网格线
        xAxis.setDrawAxisLine(false); // 轴线


        //用来限制X轴数量
        xAxis.setLabelCount(5);
        xAxis.setSpaceMin(5);
        xAxis.setSpaceMax(20);

        xAxis.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  // 设置位于底部
//        xAxis.setAvoidFirstLastClipping(true); // 设置首尾的值是否自动调整，避免被遮挡
        xAxis.setLabelCount(5, false);

        //Y轴
        YAxis axisLeft = getAxisLeft();
        axisLeft.setDrawLabels(true);
        axisLeft.setDrawGridLines(true);
        axisLeft.setDrawAxisLine(false);
        axisLeft.setGridLineWidth(1);
        axisLeft.setLabelCount(5, false);

        axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeft.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisLeft.setGridColor(getResources().getColor(R.color.minute_zhoutv));
        axisLeft.enableGridDashedLine(10, 10, 0);
        axisLeft.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisLeft.setSpaceTop(10);//距离顶部留白


        //y轴右边
        YAxis axisRight = getAxisRight();
        axisRight.setDrawLabels(true);
        axisRight.setDrawGridLines(false);
        axisRight.setDrawAxisLine(false);
        axisRight.setTextColor(getResources().getColor(R.color.common_white));
        axisRight.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisRight.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisRight.setSpaceTop(10);//距离顶部留白
    }

    public void setViewData(ArrayList<MinutesBean> minData) {
        this.mDatas = minData;

        if (mDatas.size() == 0) {
            setNoDataText("暂无数据");
            return;
        }

        setLineData();


    }

    private void setLineData() {

        //设置y左右两轴最大最小值
//        getAxisLeft().setAxisMinimum(minData.getMin());
//        getAxisLeft().setAxisMaximum(mData.getMax());
//        getAxisRight().setAxisMinimum(mData.getPercentMin());
//        getAxisRight().setAxisMaximum(mData.getPercentMax());

        //基准线
        LimitLine ll = new LimitLine(0);
        ll.setLineWidth(1f);
        ll.setLineColor(getResources().getColor(R.color.minute_jizhun));
        ll.enableDashedLine(10f, 10f, 0f);
        ll.setLineWidth(1);
        getAxisRight().addLimitLine(ll);
//        getAxisRight().setBaseValue(0);


        LineData lineData = new LineData();
        ArrayList<Entry> cjentries = new ArrayList<>();
        ArrayList<Entry> jjentries = new ArrayList<>();

        for (int i = 0; i < mDatas.size(); i++) {
            MinutesBean bean = mDatas.get(i);

            if (bean== null) {
                cjentries.add(new Entry(Float.NaN, i));
                jjentries.add(new Entry(Float.NaN, i));
                continue;
            }

//            if (!TextUtils.isEmpty(stringSparseArray.get(i)) &&
//                    stringSparseArray.get(i).contains("/")) {
//                i++;
//            }


            Entry cjentry = new Entry(i, bean.cjprice);
            Entry jjentry = new Entry(i, bean.avprice);
            cjentries.add(cjentry);
            jjentries.add(jjentry);
        }
        LineDataSet cjdataSet = new LineDataSet(cjentries, "成交价");
        LineDataSet jjdataSet = new LineDataSet(jjentries, "均价");

        cjdataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        cjdataSet.setDrawCircles(false);
        jjdataSet.setDrawCircles(false);
        cjdataSet.setCircleRadius(0);
        jjdataSet.setCircleRadius(0);
        cjdataSet.setColor(getResources().getColor(R.color.minute_blue));
        jjdataSet.setColor(getResources().getColor(R.color.minute_yellow));
        cjdataSet.setHighLightColor(Color.WHITE);
        jjdataSet.setHighlightEnabled(false);
        cjdataSet.setDrawFilled(true);


        lineData.addDataSet(cjdataSet);
        lineData.addDataSet(jjdataSet);
        setData(lineData);
        invalidate();
    }


    public void setEvent() {

        //这个不需要联动滑动，只需要一个高亮线联动
        setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    @Override
    protected void drawMarkers(Canvas canvas) {
        super.drawMarkers(canvas);

    }
}
