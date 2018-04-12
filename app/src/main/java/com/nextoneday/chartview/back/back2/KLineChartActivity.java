package com.nextoneday.chartview.back.back2;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.BarLineChartTouchListener;
import com.nextoneday.chartview.R;
import com.nextoneday.chartview.back.back2.bean.ConstantTest;
import com.nextoneday.chartview.back.back2.bean.DataParse;
import com.nextoneday.chartview.back.back2.bean.KLineBean;
import com.nextoneday.chartview.listener.CoupleChartGestureListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名: AndroidChart
 * @包名: com.test.androidchart.chart
 * @创建者: shah
 * @创建时间: 2017/4/25	15:08
 * @描述: TODO
 *
 *
 * @svn版本:
 * @更新人:
 * @更新时间:
 * @更新描述: TODO
 */
public class KLineChartActivity extends AppCompatActivity

{


    private CombinedChart        mChart;
//    private BarChart             mBarChart;
    XAxis  xAxisK;
    YAxis axisLeftBar, axisLeftK;
    YAxis axisRightBar, axisRightK;
    BarDataSet barDataSet;
    private BarLineChartTouchListener  mChartTouchListener;
    private CoupleChartGestureListener coupleChartGestureListener;
    float sum = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            mBarChart.setAutoScaleMinMaxEnabled(true);
            mChart.setAutoScaleMinMaxEnabled(true);

            mChart.notifyDataSetChanged();
//            mBarChart.notifyDataSetChanged();

            mChart.invalidate();
//            mBarChart.invalidate();

        }
    };
    private ArrayList<String> mXVals;
    private ArrayList<KLineBean> mKLineDatas;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kline);

        mChart = (CombinedChart) findViewById(R.id.combinedchart);
//        mBarChart = (BarChart) findViewById(R.id.barchart);


        initView();
        initData();

        initEvent();
    }

    private void initEvent() {

    }

    private void initData() {

//        QuoteManager.getInstance().init("139.199.250.210",8010,"jzcs","a123456",this);
        DataParse mData = new DataParse();
        JSONObject object = null;
        try {
            object = new JSONObject(ConstantTest.KLINEURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mData.parseKLine(object);

        mKLineDatas = mData.getKLineDatas();


        setData(mKLineDatas);

    }

    private void initView() {

        //combined

        initChart();
//        getOffLineData();


    }

    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }

    private BarData generateBarData(List<BarEntry> barEntries) {

        barDataSet = new BarDataSet(barEntries, "成交量");
        //        barDataSet.setBarSpacePercent(50); //bar空隙
        barDataSet.setHighlightEnabled(true);
        barDataSet.setHighLightAlpha(255);
        barDataSet.setHighLightColor(Color.WHITE);
        barDataSet.setDrawValues(false);
        barDataSet.setColor(Color.RED);
        BarData barData = new BarData(barDataSet);
        return barData;

//        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
//        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();
//
//        for (int index = 0; index < 12; index++) {
//            entries1.add(new BarEntry(0, getRandom(25, 25)));
//
//            // stacked
//            entries2.add(new BarEntry(0, new float[]{getRandom(13, 12), getRandom(13, 12)}));
//        }
//
//        BarDataSet set1 = new BarDataSet(entries1, "Bar 1");
//        set1.setColor(Color.rgb(60, 220, 78));
//        set1.setValueTextColor(Color.rgb(60, 220, 78));
//        set1.setValueTextSize(10f);
//        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
//
//        BarDataSet set2 = new BarDataSet(entries2, "");
//        set2.setStackLabels(new String[]{"Stack 1", "Stack 2"});
//        set2.setColors(new int[]{Color.rgb(61, 165, 255), Color.rgb(23, 197, 255)});
//        set2.setValueTextColor(Color.rgb(61, 165, 255));
//        set2.setValueTextSize(10f);
//        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
//
//        float groupSpace = 0.06f;
//        float barSpace = 0.02f; // x2 dataset
//        float barWidth = 0.45f; // x2 dataset
//        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"
//
//        BarData d = new BarData(set1, set2);
//        d.setBarWidth(barWidth);
//
//        // make this BarData object grouped
//        d.groupBars(0, groupSpace, barSpace); // start at x = 0
//
//        return d;
    }




    private void initChart() {
//        mBarChart.setDrawBorders(true);
//        mBarChart.setBorderWidth(1);
//        mBarChart.setBorderColor(getResources().getColor(R.color.minute_grayLine));
////        mBarChart.setDescription("");
//        mBarChart.setDragEnabled(true);
//        mBarChart.setScaleYEnabled(false);
//        mBarChart.setDoubleTapToZoomEnabled(false);
//
//        Legend barChartLegend = mBarChart.getLegend();
//        barChartLegend.setEnabled(false);
//
//        //BarYAxisFormatter  barYAxisFormatter=new BarYAxisFormatter();
//        //bar x y轴
//        xAxisBar = mBarChart.getXAxis();
//
//        xAxisBar.setDrawLabels(true);
//        xAxisBar.setDrawGridLines(false);
//        xAxisBar.setDrawAxisLine(false);
//        xAxisBar.setTextColor(getResources().getColor(R.color.minute_zhoutv));
//        xAxisBar.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxisBar.setGridColor(getResources().getColor(R.color.minute_grayLine));
//
//        axisLeftBar = mBarChart.getAxisLeft();
//        axisLeftBar.setAxisMinValue(0);
//        axisLeftBar.setDrawGridLines(false);
//        axisLeftBar.setDrawAxisLine(false);
//        axisLeftBar.setTextColor(getResources().getColor(R.color.minute_zhoutv));
//        axisLeftBar.setDrawLabels(true);
//        axisLeftBar.setSpaceTop(0);
////        axisLeftBar.setShowOnlyMinMax(true);
//
//        axisRightBar = mBarChart.getAxisRight();
//        axisRightBar.setDrawLabels(false);
//        axisRightBar.setDrawGridLines(false);
//        axisRightBar.setDrawAxisLine(false);
//



        /****************************************************************/
        mChart.setDoubleTapToZoomEnabled(false);
        mChart.setDrawBorders(true);
        mChart.setBorderWidth(1);
        mChart.setBorderColor(getResources().getColor(R.color.minute_grayLine));
//        mChart.setDescription("");
        mChart.setDragEnabled(true);
        mChart.setScaleYEnabled(false);

        Legend combinedchartLegend = mChart.getLegend();
        combinedchartLegend.setEnabled(false);
        //bar x y轴
        xAxisK = mChart.getXAxis();
        xAxisK.setEnabled(false);
        xAxisK.setDrawLabels(true);
        xAxisK.setDrawGridLines(false);
        xAxisK.setDrawAxisLine(false);
        xAxisK.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        xAxisK.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisK.setGridColor(getResources().getColor(R.color.minute_grayLine));



        axisLeftK = mChart.getAxisLeft();
        axisLeftK.setDrawGridLines(true);
        axisLeftK.setDrawAxisLine(false);
        axisLeftK.setDrawLabels(true);
        axisLeftK.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisLeftK.setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftK.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisRightK = mChart.getAxisRight();
        axisRightK.setDrawLabels(false);
        axisRightK.setDrawGridLines(true);
        axisRightK.setDrawAxisLine(false);
        axisRightK.setGridColor(getResources().getColor(R.color.minute_grayLine));
        mChart.setDragDecelerationEnabled(true);
//        mBarChart.setDragDecelerationEnabled(true);
        mChart.setDragDecelerationFrictionCoef(0.2f);
//        mBarChart.setDragDecelerationFrictionCoef(0.2f);

        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                  CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE});


//        // 将K线控的滑动事件传递给交易量控件
//        mChart.setOnChartGestureListener(new CoupleChartGestureListener(mChart,
//                                                                               new Chart[]{mBarChart}));
//        // 将交易量控件的滑动事件传递给K线控件
//        mBarChart.setOnChartGestureListener(new CoupleChartGestureListener(mBarChart,
//                                                                          new Chart[]{mChart}));
//        mBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//
//
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//                Log.e("%%%%", h.getX() + "");
//                mChart.highlightValues(new Highlight[]{h});
//            }
//
//            @Override
//            public void onNothingSelected() {
//                mChart.highlightValue(null);
//            }
//        });
//        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//
//
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//                mBarChart.highlightValues(new Highlight[]{h});
//            }
//
//            @Override
//            public void onNothingSelected() {
//                mBarChart.highlightValue(null);
//            }
//        });


    }

    private float getSum(Integer a, Integer b) {

        for (int i = a; i <= b; i++) {
            sum += mKLineDatas.get(i).close;
        }
        return sum;
    }

    private float culcMaxscale(float count) {
        float max = 1;
        max = count / 127 * 5;
        return max;
    }



    protected CandleData generateCandleData(List<CandleEntry> candleEntries) {


        CandleDataSet candleDataSet = new CandleDataSet(candleEntries, "KLine");
        candleDataSet.setDrawHorizontalHighlightIndicator(false);
        candleDataSet.setHighlightEnabled(true);
        candleDataSet.setHighLightColor(Color.WHITE);
        candleDataSet.setValueTextSize(10f);
        candleDataSet.setDrawValues(false);
        candleDataSet.setColor(Color.RED);
        candleDataSet.setShadowWidth(1f);
        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        CandleData candleData = new CandleData(candleDataSet);

        return  candleData;
//        CandleData d = new CandleData();
//
//        ArrayList<CandleEntry> entries = new ArrayList<CandleEntry>();
//
//        for (int index = 0; index < 20; index += 2)
//            entries.add(new CandleEntry(index + 1f, 90, 70, 85, 75f));
//
//        CandleDataSet set = new CandleDataSet(entries, "Candle DataSet");
//        set.setDecreasingColor(Color.rgb(142, 150, 175));
//        set.setShadowColor(Color.DKGRAY);
//        set.setBarSpace(0.3f);
//        set.setValueTextSize(10f);
//        set.setDrawValues(false);
//        d.addDataSet(set);

    }

    private void setData(List<KLineBean> mData) {
        mXVals = new ArrayList<>();
        ArrayList<CandleEntry> candleEntries = new ArrayList<>();
        ArrayList<Entry>       line5Entries  = new ArrayList<>();
        ArrayList<Entry>       line10Entries = new ArrayList<>();
        ArrayList<Entry>       line30Entries = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            mXVals.add(mData
                           .get(i).date + "");

            candleEntries.add(new CandleEntry(i,
                                              mData.get(i).high,
                                              mData.get(i).low,
                                              mData.get(i).open, mData.get(i).close));
            if (i >= 4) {
                sum = 0;
                line5Entries.add(new Entry(getSum(i - 4, i) / 5, i));
            }
            if (i >= 9) {
                sum = 0;
                line10Entries.add(new Entry(getSum(i - 9, i) / 10, i));
            }
            if (i >= 29) {
                sum = 0;
                line30Entries.add(new Entry(getSum(i - 29, i) / 30, i));
            }

        }



        //下面的条形图
//        mBarChart.setData(generateBarData(barEntries));


//        final ViewPortHandler viewPortHandlerBar = mBarChart.getViewPortHandler();
//        viewPortHandlerBar.setMaximumScaleX(culcMaxscale(mXVals.size()));
//        Matrix      touchmatrix = viewPortHandlerBar.getMatrixTouch();
//        final float xscale      = 3;
//        touchmatrix.postScale(xscale, 1f);



        //综合图
        CombinedData combinedData = new CombinedData();

        //蜡烛图
        CandleData candleData = generateCandleData(candleEntries);

        //ma line
        LineData lineData = generateLineData(mXVals,
                                             line5Entries,
                                             line10Entries,
                                             line30Entries);


        combinedData.setData(candleData);
        combinedData.setData(lineData);
        mChart.setData(combinedData);


        mChart.invalidate();
        mChart.notifyDataSetChanged();
//        mChart.moveViewToX(mData.size() - 1);
//        final ViewPortHandler viewPortHandlerCombin = mChart.getViewPortHandler();
//        viewPortHandlerCombin.setMaximumScaleX(culcMaxscale(mXVals.size()));
//        Matrix      matrixCombin = viewPortHandlerCombin.getMatrixTouch();
//        final float xscaleCombin = 3;
//        matrixCombin.postScale(xscaleCombin, 1f);
//
//        mChart.moveViewToX(mData.size() - 1);
////        mBarChart.moveViewToX(mData.size() - 1);
////        setOffset();
//
//        mChart.invalidate();

        /****************************************************************************************
         此处解决方法来源于CombinedChartDemo，k线图y轴显示问题，图表滑动后才能对齐的bug，希望有人给出解决方法
         (注：此bug现已修复，感谢和chenguang79一起研究)
         ****************************************************************************************/

//        handler.sendEmptyMessageDelayed(0, 300);

    }

    private LineData generateLineData(ArrayList<String> xVals,ArrayList<Entry> line5Entries,
                                  ArrayList<Entry>line10Entries,ArrayList<Entry>line30Entries) {
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        int size = mKLineDatas.size();
        /******此处修复如果显示的点的个数达不到MA均线的位置所有的点都从0开始计算最小值的问题******************************/
        if (size >= 30) {
            sets.add(setMaLine(5, xVals, line5Entries));
            sets.add(setMaLine(10, xVals, line10Entries));
            sets.add(setMaLine(30, xVals, line30Entries));
        } else if (size >= 10 && size < 30) {
            sets.add(setMaLine(5, xVals, line5Entries));
            sets.add(setMaLine(10, xVals, line10Entries));
        } else if (size >= 5 && size < 10) {
            sets.add(setMaLine(5, xVals, line5Entries));
        }
        LineData lineData = new LineData(sets);
        return lineData;
    }

    @NonNull
    private LineDataSet setMaLine(int ma, ArrayList<String> xVals, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + ma);
        if (ma == 5) {
            lineDataSetMa.setHighlightEnabled(true);
            lineDataSetMa.setDrawHorizontalHighlightIndicator(false);
            lineDataSetMa.setHighLightColor(Color.WHITE);
        } else {/*此处必须得写*/
            lineDataSetMa.setHighlightEnabled(false);
        }
        lineDataSetMa.setDrawValues(false);
        if (ma == 5) {
            lineDataSetMa.setColor(Color.GREEN);
        } else if (ma == 10) {
            lineDataSetMa.setColor(Color.GRAY);
        } else {
            lineDataSetMa.setColor(Color.YELLOW);
        }
        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
        return lineDataSetMa;
    }

//    /*设置量表对齐*/
//    private void setOffset() {
//        float lineLeft  = mChart.getViewPortHandler()
//                                       .offsetLeft();
////        float barLeft   = mBarChart.getViewPortHandler()
////                                  .offsetLeft();
//        float lineRight = mChart.getViewPortHandler()
//                                       .offsetRight();
////        float barRight  = mBarChart.getViewPortHandler()
////                                  .offsetRight();
////        float barBottom = mBarChart.getViewPortHandler()
////                                  .offsetBottom();
//        float offsetLeft, offsetRight;
//        float transLeft = 0, transRight = 0;
// /*注：setExtraLeft...函数是针对图表相对位置计算，比如A表offLeftA=20dp,B表offLeftB=30dp,则A.setExtraLeftOffset(10),并不是30，还有注意单位转换*/
//        if (barLeft < lineLeft) {
//           /* offsetLeft = Utils.convertPixelsToDp(lineLeft - barLeft);
//            barChart.setExtraLeftOffset(offsetLeft);*/
//            transLeft = lineLeft;
//        } else {
//            offsetLeft = Utils.convertPixelsToDp(barLeft - lineLeft);
//            mChart.setExtraLeftOffset(offsetLeft);
//            transLeft = barLeft;
//        }
//  /*注：setExtraRight...函数是针对图表绝对位置计算，比如A表offRightA=20dp,B表offRightB=30dp,则A.setExtraLeftOffset(30),并不是10，还有注意单位转换*/
//        if (barRight < lineRight) {
//          /*  offsetRight = Utils.convertPixelsToDp(lineRight);
//            barChart.setExtraRightOffset(offsetRight);*/
//            transRight = lineRight;
//        } else {
//            offsetRight = Utils.convertPixelsToDp(barRight);
//            mChart.setExtraRightOffset(offsetRight);
//            transRight = barRight;
//        }
//        mBarChart.setViewPortOffsets(transLeft, 15, transRight, barBottom);
//    }





}


