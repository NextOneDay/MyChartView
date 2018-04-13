package com.nextoneday.chartview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.nextoneday.chartview.R;
import com.nextoneday.chartview.back.back2.bean.KLineBean;
import com.nextoneday.chartview.listener.CoupleChartGestureListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/13.
 */

public class OtherViewChart extends CombinedChart {
    private ArrayList<KLineBean> mDatas;
    private ArrayList<String> mXVals;
    private ArrayList<Entry> mDifline;
    private ArrayList<Entry> mDealine;

    public OtherViewChart(Context context) {
        super(context);
        initView();
    }

    public OtherViewChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OtherViewChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    /**
     * 初始化其他指标线的view
     */

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
        axisRight.setLabelCount(4, false); //第一个参数数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisRight.setSpaceTop(10);//距白
    }


    /**
     * 这里需要gezhong各种指标，的显示，计算
     * <p>
     * 默认显示的是macd线
     *
     * @param kLineDatas
     */
    public void setViewData(ArrayList<KLineBean> kLineDatas) {

        this.mDatas = kLineDatas;

        setCombinedData();
    }

    /**
     * 在这里设置将计算后 不同指标图，添加到combined中
     */
    private void setCombinedData() {

        CombinedData combinedData = new CombinedData();
        combinedData.setData(generateBarData());
        combinedData.setData(generateLineData());

        setData(combinedData);
        setHandler();
    }

    /**
     * 设置boll 线
     */
    private void setBollLineData() {

        CombinedData combinedData = new CombinedData();
        combinedData.setData(generateBarData());
        combinedData.setData(generateLineData());
        setData(combinedData);
        setHandler();

    }

    private void setKDJLineData() {


    }

    private void setRSILineData() {

    }

    private LineData generateLineData() {
        LineData lineData = new LineData();

        lineData.addDataSet(lineDataSet(0, mDealine));
        lineData.addDataSet(lineDataSet(1, mDifline));


        return lineData;
    }

    private LineDataSet lineDataSet(int type, ArrayList<Entry> line) {


        LineDataSet lineDataSet = new LineDataSet(line, "macd" + type);
        lineDataSet.setHighlightEnabled(false);
        lineDataSet.setDrawValues(false);

        //DEA
        if (type == 0) {
            lineDataSet.setColor(getResources().getColor(R.color.ma5));
        } else {
            lineDataSet.setColor(getResources().getColor(R.color.ma10));
        }

        lineDataSet.setLineWidth(1f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        return lineDataSet;
    }

    /**
     * MACD线  包含dea  dif  macd
     * 其中基础计算时ema12 ema26 这个没有显示，但是是其他计算的基础
     *
     * @return
     */
    private BarData generateBarData() {

        BarData barData = new BarData();
        ArrayList<BarEntry> entries = new ArrayList<>();
        mDealine = new ArrayList<>();
        mDifline = new ArrayList<>();
        mXVals = new ArrayList<>();
        // 添加指标
        // 1、计算移动平均值（EMA）  
        // 12日EMA的算式为： 
        // EMA（12）=前一日EMA（12）×11/13＋今日收盘价×2/13  
        // 26日EMA的算式为： 
        // EMA（26）=前一日EMA（26）×25/27＋今日收盘价×2/27  
        // 2、计算离差值（DIF）  
        // DIF=今日EMA（12）－今日EMA（26）  3、计算DIF的9日EMA （DEA） 
        // 3、计算DIF的9日EMA （DEA） 
        // 根据离差值计算其9日的EMA，即离差平均值，是所求的MACD值。为了不与指标原名相混淆，此值又名DEA或DEM。  
        // 今日DEA（MACD）=前一日DEA×8/10＋今日DIF×2/10  
        // 4、计算MACD
        // MACD=BAR=2×(DIF－DEA)

        ArrayList<Float> DEALine = new ArrayList<>();
        ArrayList<Float> DIFLine = new ArrayList<>();
        ArrayList<Float> MACDLine = new ArrayList<>();

        float ema12 = 0.0f;
        float ema26 = 0.0f;
        float dif, macd, dea = 0.0f;
        float close;
        if (mDatas != null || mDatas.size() > 0) {
            for (int i = 0; i < mDatas.size(); i++) {
                KLineBean bean = mDatas.get(i);
                mXVals.add(bean.date);
                close = bean.close;
                if (i == 0) {
                    ema12 = close;
                    ema26 = close;
                } else {
                    ema12 = ema12 * 11 / 13 + close * 2 / 13;
                    ema26 = ema26 * 25 / 27 + close * 2 / 27;
                }
                dif = ema12 - ema26;
                dea = dea * 8 / 10 + dif * 2 / 10;
                macd = 2 * (dif - dea);

                DEALine.add(dea);
                DIFLine.add(dif);
                MACDLine.add(macd);

                BarEntry entry = new BarEntry(i, macd);
                entries.add(entry);
                Entry deaentry = new Entry(i, dea);
                mDealine.add(deaentry);
                Entry difentry = new Entry(i, dif);
                mDifline.add(difentry);

            }
        }

        BarDataSet barDataSet = new BarDataSet(entries, "MACD");
        //对bar设置属性，颜色等值

        barDataSet.setHighlightEnabled(true);
        barDataSet.setHighLightAlpha(255);
        barDataSet.setHighLightColor(getResources().getColor(R.color.common_white));
        barDataSet.setDrawValues(false);
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        barDataSet.setColors(getResources().getColor(R.color.text_red));

        barData.addDataSet(barDataSet);
        return barData;
    }


    private void setHandler() {

        final ViewPortHandler viewPortHandlerBar = getViewPortHandler();
        viewPortHandlerBar.setMaximumScaleX(culcMaxscale(mXVals.size()));
        viewPortHandlerBar.setMinimumScaleX(1f);
        Matrix touchmatrix = viewPortHandlerBar.getMatrixTouch();
        final float xscale = 3;
        touchmatrix.postScale(xscale, 1f);
    }

    private float culcMaxscale(float count) {
        float max = 1;
        max = count / 127 * 5;
        return max;
    }

    public void setEvent(Chart[] charts) {

        setOnChartGestureListener(new CoupleChartGestureListener(this, charts));

        setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });
    }


    /**
     * 重写markerview 的显示
     *
     * @param canvas
     */
    @Override
    protected void drawMarkers(Canvas canvas) {
        super.drawMarkers(canvas);
    }
}
