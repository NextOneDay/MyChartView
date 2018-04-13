package com.nextoneday.chartview.view;

import android.content.Context;
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
import com.nextoneday.chartview.back.CoupleChartGestureListener;
import com.nextoneday.chartview.back.back2.bean.KLineBean;
import com.nextoneday.chartview.back.back2.bean.MyUtils;
import com.nextoneday.chartview.back.back2.bean.VolFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/13.
 * <p>
 * 这个图的大致结构与上面的k线图一直，有几种类型的线形成
 * 成交量
 */

public class BandsLineChart extends CombinedChart {
    private ArrayList<KLineBean> mDatas;
    private float maxVolume;
    private ArrayList<String> mXVals;

    public BandsLineChart(Context context) {
        super(context);
        initView();
    }


    public BandsLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BandsLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }


    /**
     * 初始化chart 的设置
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
        axisRight.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisRight.setSpaceTop(10);//距离顶部留白
    }

    public void setViewData(ArrayList<KLineBean> kLineDatas, float volmax) {
        this.mDatas = kLineDatas;
        this.maxVolume = volmax;

        setCombinedData();

    }

    /**
     * 设置 Combinedchart中的图形以及数据
     */
    private void setCombinedData() {

        CombinedData combinedData = new CombinedData();
        combinedData.setData(generateBarData());
        combinedData.setData(generateLineData());

        //设置数据，
        setData(combinedData);


        setHandler();// 需要设置最大的缩放

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
    /**
     * 这个跟K线图中的平均线很类似
     *
     * @return
     */
    private LineData generateLineData() {
        LineData lineData = new LineData();
        List<ArrayList<Entry>> arrayLists = parseVolumeMaLine();

        lineData.addDataSet(lineDataSet(5, arrayLists.get(0)));
        lineData.addDataSet(lineDataSet(10, arrayLists.get(1)));
        lineData.addDataSet(lineDataSet(20, arrayLists.get(2)));

        return lineData;
    }

    private LineDataSet lineDataSet(int num, ArrayList<Entry> entries) {

        LineDataSet lineDataSet = new LineDataSet(entries, "VMA" + num);

        if (num == 5) {
            lineDataSet.setHighlightEnabled(true);
            lineDataSet.setDrawHorizontalHighlightIndicator(true);
            lineDataSet.setHighLightColor(getResources().getColor(R.color.common_white));
        } else {
            lineDataSet.setHighlightEnabled(false);
        }
        if (num == 5) {
            lineDataSet.setColor(getResources().getColor(R.color.ma5));
        } else if (num == 10) {

            lineDataSet.setColor(getResources().getColor(R.color.ma10));
        } else if (num == 20) {
            lineDataSet.setColor(getResources().getColor(R.color.ma20));
        }
        lineDataSet.setHighlightEnabled(false);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        return lineDataSet;
    }

    private List<ArrayList<Entry>> parseVolumeMaLine() {
        ArrayList<Entry> vma5 = new ArrayList<>();
        ArrayList<Entry> vma10 = new ArrayList<>();
        ArrayList<Entry> vma20 = new ArrayList<>();

        List<ArrayList<Entry>> vmaLine = new ArrayList<>();

        for (int i = 0; i < mDatas.size(); i++) {
            if (i > 4) {
                vma5.add(new Entry(i, getSum(i - 4, i) / 5));
            }

            if (i >= 9) {
                vma10.add(new Entry(i, getSum(i - 9, i) / 10));
            }
            if (i >= 19) {

                vma20.add(new Entry(i, getSum(i - 19, i) / 20));
            }

        }

        vmaLine.add(vma5);
        vmaLine.add(vma10);
        vmaLine.add(vma20);

        return vmaLine;
    }

    private int getSum(int index, int totle) {

        int sum = 0;
        for (int i = index; i <= totle; i++) {
            sum += mDatas.get(i).vol;
        }
        return sum;
    }

    /**
     * 设置成交量的的数据图形
     *
     * @return
     */
    private BarData generateBarData() {
        String unit = MyUtils.getVolUnit(maxVolume);
        String wan = getContext().getString(R.string.wan_unit);
        String yi = getContext().getString(R.string.yi_unit);
        int u = 1;
        if (wan.equals(unit)) {
            u = 4;
        } else if (yi.equals(unit)) {
            u = 8;
        }
        getAxisLeft().setValueFormatter(new VolFormatter((int) Math.pow(10, u)));
        // 把如果数据的值比较大，转换一下单位
        getAxisRight().setValueFormatter(new VolFormatter((int) Math.pow(10, u)));

        BarData barData = new BarData();

        ArrayList<BarEntry> entries = new ArrayList<>();
        mXVals = new ArrayList<>();

        for (int i = 0; i < mDatas.size(); i++) {
            KLineBean bean = mDatas.get(i);
            mXVals.add(bean.date);
            BarEntry entry = new BarEntry(i, bean.vol);
            entries.add(entry);

        }
        BarDataSet barDataSet = new BarDataSet(entries, "成交量");
        //在这里设置一些属性、颜色等值。
//        barDataSet.setBarBorderWidth(20);
//        barDataSet.setBarSpacePercent(20); //bar空隙
        barDataSet.setHighlightEnabled(true);
        barDataSet.setHighLightAlpha(255);
        barDataSet.setHighLightColor(getResources().getColor(R.color.common_white));
        barDataSet.setDrawValues(false);

        List<Integer> list = new ArrayList<>();
        list.add(getResources().getColor(R.color.text_red));
        list.add(getResources().getColor(R.color.text_green));
        barDataSet.setColors(list);

        barData.addDataSet(barDataSet);
        return barData;
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
     * 重新绘制markerview
     *
     * @param enabled
     */
    @Override
    public void setDrawMarkers(boolean enabled) {
        super.setDrawMarkers(enabled);
    }
}
