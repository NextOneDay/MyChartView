package com.nextoneday.chartview.back2;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.nextoneday.chartview.R;
import com.nextoneday.chartview.bean.ConstantTest;
import com.nextoneday.chartview.bean.DataParse;
import com.nextoneday.chartview.bean.KLineBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名: StockChart-master
 * @包名: com.example.yanjiang.stockchart
 * @创建者: shah
 * @创建时间: 2017/4/24	15:43
 * @描述: TODO
 *
 *
 * @svn版本:
 * @更新人:
 * @更新时间:
 * @更新描述: TODO
 */
public class MyKLineChart
        extends AppCompatActivity
        implements OnChartValueSelectedListener

{


    private CombinedChart       mChart;
    private XAxis               mXAxis;
    private int                 sum;

    private ArrayList<KLineBean> mKLineDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_klinechart);
        mChart = (CombinedChart) findViewById(R.id.chart);

        initView();

         initKLineView();

        initEvent();

        DataParse mData = new DataParse();
        JSONObject object = null;
        try {
            object = new JSONObject(ConstantTest.KLINEURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mData.parseKLine(object);

        mKLineDatas = mData.getKLineDatas();

        initData(mKLineDatas);
    }

    private void initKLineView() {


    }

    private void initEvent() {

//                mChart.setOnChartGestureListener(new CoupleChartGestureListener(mChart, null));
        mChart.setOnChartValueSelectedListener(this);
    }

    protected Typeface mTfLight;

    private void initData(final List<KLineBean> mData) {


        CombinedData data = new CombinedData();

                data.setData(generateCandleData(mData));

        data.setData(generateLineData(mData));
        mXAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                float         v             = value % mData.size();
                KLineBean quoteHisKUnit = mData.get((int) v);
                String        s             = String.valueOf(quoteHisKUnit.date);
                return s;

            }
        });

        data.setValueTypeface(mTfLight);
        mXAxis.setAxisMaximum(data.getXMax() + 0.25f);
        mChart.setData(data);

        mChart.invalidate();


    }

    private float getSum(Integer a, Integer b) {

        for (int i = a; i <= b; i++) {
            sum += mKLineDatas.get(i).close;
        }
        return sum;
    }

    private LineData generateLineData(List<KLineBean> list) {

        ArrayList<Entry> line5  = new ArrayList<>();
        ArrayList<Entry> line10 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (i >= 4) {
                sum = 0;
                line5.add(new Entry(getSum(i - 4, i) / 5, i));
            }
            if (i >= 9) {
                sum = 0;
                line10.add(new Entry(getSum(i - 9, i) / 10, i));
            }

        }


        List<ILineDataSet> sets = new ArrayList<>();
        //
        //        int size= mList.size();
        //        if (size >= 30) {
        //            sets.add();
        //            sets.add(setMaLine(10,  line10));
        //        } else if (size >= 10 && size < 30) {
        //            sets.add(setMaLine(5,  line5));
        //            sets.add(setMaLine(10,  line10));
        //        } else if (size >= 5 && size < 10) {
        //            sets.add(setMaLine(5,  line5));
        //        }
//        sets.add(setMaLine(5, line5));
//        sets.add(setMaLine(10, line10));


        LineData lineData = new LineData(setMaLine(5,line5));


        return lineData;

    }

    private LineDataSet setMaLine(int ma, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + ma);
        if (ma == 5) {
            lineDataSetMa.setHighlightEnabled(true);
            lineDataSetMa.setDrawHorizontalHighlightIndicator(true);
            lineDataSetMa.setHighLightColor(Color.WHITE);
        }
        lineDataSetMa.setDrawValues(false);
        if (ma == 5) {
            lineDataSetMa.setColor(Color.WHITE);
        } else if (ma == 10) {
            lineDataSetMa.setColor(Color.YELLOW);
        }
        lineDataSetMa.setDrawCircles(false);
        //        lineDataSetMa.setDrawCubic(true);//圆滑曲线
        lineDataSetMa.setDrawCircleHole(false);
        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
        return lineDataSetMa;
    }


    private CandleData generateCandleData(List<KLineBean> mData) {

        CandleData candleData = new CandleData();

        ArrayList<CandleEntry> al = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {

            al.add(new CandleEntry(i + 1f,
                                   mData.get(i).high,
                                   mData.get(i).low,
                                   mData.get(i).open,
                                   mData.get(i).close));
        }

        CandleDataSet mDataset = new CandleDataSet(al, "K线数据");

        mDataset.setAxisDependency(YAxis.AxisDependency.LEFT);
        //        set1.setColor(Color.rgb(80, 80, 80));
        mDataset.setShadowColor(Color.DKGRAY);
        mDataset.setShadowWidth(0.7f);
        mDataset.setDecreasingColor(Color.CYAN);
        mDataset.setDecreasingPaintStyle(Paint.Style.FILL);
        mDataset.setIncreasingColor(Color.RED);
        mDataset.setIncreasingPaintStyle(Paint.Style.STROKE);
        mDataset.setNeutralColor(Color.RED);//开盘等于关闭盘的时候
        mDataset.setHighlightLineWidth(1f);//选中蜡烛时的线宽
        mDataset.setHighlightEnabled(true);
        mDataset.setHighLightColor(Color.WHITE);
        mDataset.setShadowColorSameAsCandle(true);
        mDataset.setDrawValues(true);
        mDataset.setValueTextColor(Color.WHITE);
        //        mDataset.setBarSpace(0.3f);
        mDataset.setValueTextSize(10f);

        candleData.addDataSet(mDataset);

        return candleData;

    }


    private void initView() {


        mChart.setDrawBorders(false);

        mChart.setDoubleTapToZoomEnabled(false);//禁止双击
        mChart.setMaxVisibleValueCount(60);

        mChart.setDrawGridBackground(false); // 是否显示表格颜色
        mChart.setBackgroundColor(Color.BLACK);// 设置背景
        mChart.setGridBackgroundColor(Color.BLACK);//设置表格背景色
        mChart.setDragEnabled(true);// 是否可以拖拽
        mChart.setScaleEnabled(true);// 是否可以缩放
        mChart.setScaleYEnabled(false);// if disabled, scaling can be done on x-axis

        mChart.animateX(2500); // 立即执行的动画,x轴

        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.CANDLE,
                                                          CombinedChart.DrawOrder.LINE});


        mXAxis = mChart.getXAxis();
        mXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mXAxis.setDrawGridLines(false);
        mXAxis.setGridColor(Color.WHITE);//X轴刻度线颜色
        mXAxis.setTextColor(Color.WHITE);//X轴文字颜色
        mXAxis.setGridColor(Color.GRAY);//X轴刻度线颜色
        mXAxis.setSpaceMax(20);
        mXAxis.setSpaceMin(5);


        //y轴

        YAxis axisLeft = mChart.getAxisLeft();
        axisLeft.setLabelCount(10, false);
        axisLeft.setDrawGridLines(false);
        axisLeft.setGridColor(Color.WHITE);
        axisLeft.setTextColor(Color.WHITE);
        axisLeft.setGridColor(Color.GRAY);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);


        //图例
        Legend l = mChart.getLegend();
        l.setEnabled(false);
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

        System.out.println(e.toString() + h.toString() + "onValueSelected");
        mChart.highlightValues(new Highlight[]{h});
    }

    @Override
    public void onNothingSelected() {

        System.out.println("onNothingSelected");
        mChart.highlightValues(null);
    }








}
