package com.nextoneday.chartview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.nextoneday.chartview.R;
import com.nextoneday.chartview.back.back2.bean.KLineBean;
import com.nextoneday.chartview.listener.CoupleChartGestureListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/12.
 * <p>
 * 1.放在布局中就能够使用，
 * 2.设置数据就能够显示数据
 */

public class MyKlineChart extends CombinedChart {


    private static final String TAG = "MyKlineChart";
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
     */

    private void initView() {

        setBackgroundFrame();
        setChartGestureSet();
        setXY();
//        setEvent();

    }

    /**
     * 在这里需要给他们设置颜色、属性等值
     *
     * @param maNum
     * @param maline
     * @return
     */
    private LineDataSet setLineDataSetProperty(int maNum, ArrayList<Entry> maline) {


        LineDataSet maData = new LineDataSet(maline, "MA" + maNum);

        if (maNum == 5) {
            maData.setHighlightEnabled(true);
            maData.setDrawHorizontalHighlightIndicator(false);
            maData.setHighLightColor(getResources().getColor(R.color.BLACK));
        } else {/*此处必须得写*/
            maData.setHighlightEnabled(false);
        }
        maData.setDrawValues(true); // 设置绘制值
        if (maNum == 5) {
            maData.setColor(getResources().getColor(R.color.ma5));
        } else if (maNum == 10) {
            maData.setColor(getResources().getColor(R.color.ma10));
        } else if (maNum == 20) {
            maData.setColor(getResources().getColor(R.color.ma20));
        }
        maData.setLineWidth(1f);
        maData.setDrawCircles(false);
        maData.setAxisDependency(YAxis.AxisDependency.LEFT);

        maData.setHighlightEnabled(false);

        maData.setValueTextSize(10f);
        maData.setValueTextColor(Color.rgb(240, 238, 70));
        return maData;
    }

    /**
     * 通过外部把数据个传进来
     *
     * @param kLineDatas
     */
    public void setViewData(ArrayList<KLineBean> kLineDatas) {
        this.mDatas = kLineDatas;

        //设置x轴的数据
        XAxis xAxis = getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                 mMonths[(int) value % mMonths.length];
                String date = mDatas.get((int) value).date;
                Log.d(TAG,date+":::"+value+":::"+axis);
                return date;
            }
        });

        setCombinedData();

    }

    /**
     * 设置全部数据，并把数据添加到vie中
     */
    private void setCombinedData() {

        CombinedData combinedData = new CombinedData();
        combinedData.setData(generateCandleData());
        combinedData.setData(generateLineData());




        //设置数据后重新刷新一次
        setData(combinedData);
        invalidate();
    }

    /**
     * 创建 ma线 的数据集
     * ma5 ma10 ma20 ma30
     * 存放也是一样
     * LineData ->LineDataSet ->List<LineEntry>
     *
     * @return
     */
    // TODO: 2018/4/12 数据不应该在这里解析，
    private LineData generateLineData() {

        LineData lineData = new LineData();


        List<ArrayList<Entry>> arrayLists = parseMaLineData();


        lineData.addDataSet(setLineDataSetProperty(5, arrayLists.get(0)));
        lineData.addDataSet(setLineDataSetProperty(10, arrayLists.get(1)));
        lineData.addDataSet(setLineDataSetProperty(20, arrayLists.get(2)));


        return lineData;
    }

    /**
     * 在这里获取ma5 ma10 等线
     */

    private List<ArrayList<Entry>> parseMaLineData() {
        ArrayList<Entry> ma5 = new ArrayList<>();
        ArrayList<Entry> ma10 = new ArrayList<>();
        ArrayList<Entry> ma20 = new ArrayList<>();
        List<ArrayList<Entry>> maline = new ArrayList<>();

        for (int i = 0; i < mDatas.size(); i++) {
//            计算ma5 的计算为 前5天之和除以5天
//            计算ma10 的计算为 前10天之和除以10天
//            计算ma20 的计算为 前20天之和除以20天
            if (i >= 4) {
                ma5.add(new Entry(i,getSum(i - 4, i) / 5));
            }
            if (i >= 9) {
                ma10.add(new Entry(i,getSum(i - 9, i) / 10));
            }
            if (i >= 19) {
                ma20.add(new Entry(i,getSum(i - 19, i) / 20));
            }

        }

        maline.add(ma5);
        maline.add(ma10);
        maline.add(ma20);
        return maline;
    }


    private float getSum(Integer a, Integer b) {
        int sum = 0;
        for (int i = a; i <= b; i++) {
            sum += mDatas.get(i).close;
        }
        return sum;


    }

    /**
     * 设置蜡烛图数据
     * 数据设置有三级
     * CandleData -> CandleDataSet->ArrayList<CandleEntry>;
     *
     * @return
     */
    // TODO: 2018/4/12  这里需要优化，使用一个类来封装解析对应的数据，然后传进来，不应该在这里解析 ,目前这里就随便写写
    private CandleData generateCandleData() {
        CandleData candleData = new CandleData();
        //放置Y轴，和标签
        ArrayList<CandleEntry> candleList = new ArrayList<>();

        for (int i = 0; i < mDatas.size(); i++) {
            KLineBean bean = mDatas.get(i);
            CandleEntry entry = new CandleEntry(i, bean.high, bean.low, bean.open, bean.close);
            candleList.add(entry);
        }
        CandleDataSet candleDataSet = new CandleDataSet(candleList, "蜡烛图");

        // 在这里设置蜡烛图的一些属性颜色等

        candleDataSet.setDrawHorizontalHighlightIndicator(true);
        candleDataSet.setHighlightEnabled(true);
        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT); //相对于那个轴
        candleDataSet.setShadowWidth(1f);
        candleDataSet.setValueTextSize(10f);
        candleDataSet.setDecreasingColor(getResources().getColor(R.color.text_green));//设置开盘价高于收盘价的颜色
        candleDataSet.setDecreasingPaintStyle(Paint.Style.FILL); //实心
        candleDataSet.setIncreasingColor(getResources().getColor(R.color.text_red));//设置开盘价地狱收盘价的颜色
        candleDataSet.setIncreasingPaintStyle(Paint.Style.STROKE); // 空心
        candleDataSet.setNeutralColor(getResources().getColor(R.color.text_green));//设置开盘价等于收盘价的颜色
        candleDataSet.setShadowColorSameAsCandle(true);
        candleDataSet.setHighlightLineWidth(1f);
        candleDataSet.setHighLightColor(getResources().getColor(R.color.common_white));
        candleDataSet.setDrawValues(false);
        candleData.addDataSet(candleDataSet);

        return candleData;
    }

    /**
     * 联级滑动, 以及点击后高亮线的显示，以及修改高亮线的样式
     */
    public void setEvent(final Chart[] charts) {
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
//                charts[0].highlightValue(null);
//                charts[1].highlightValue(null);
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

        xAxis.setDrawGridLines(false); // 网格线
        xAxis.setDrawAxisLine(false); // 轴线



        xAxis.setSpaceMin(5);
        xAxis.setSpaceMax(20);

        xAxis.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  // 设置位于底部
//        xAxis.setAvoidFirstLastClipping(true); // 设置首尾的值是否自动调整，避免被遮挡


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
        //不绘制图例
        Legend legend = getLegend();
        legend.setEnabled(false);
    }


    /**
     * 可能需要先设置一些数据
     * 重写绘制markerview 的显示
     *
     * @param canvas
     */
    @Override
    protected void drawMarkers(Canvas canvas) {
        super.drawMarkers(canvas);
    }
}
