package com.nextoneday.chartview.back.back2.bean;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.nextoneday.chartview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名: AndroidChart
 * @包名: com.test.androidchart.chart
 * @创建者: shah
 * @创建时间: 2017/3/6	9:17
 * @描述: TODO
 *
 *
 * @svn版本:
 * @更新人:
 * @更新时间:
 * @更新描述: TODO
 */
public class Chartdetail
        extends AppCompatActivity
        implements OnChartValueSelectedListener, OnChartGestureListener
{


    private LineChart mChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_charts);

        initView();
        initSet();
        initData();
        initEvent();

        //具体的图标设置
        specificChartSetting();

        //说明图例的设置
        LegendSetting();

        //动态的增新数据和移除数据
        dymnamicAddData();

        //        修改窗口
        resetWindow();

        //动画
        animations();

        //       当点击图上的点时，会弹出一个View，这就是 Markerview 。
        markView();

        LineDataSetting();


    }


    private void markView() {

        //获取markview的方法
        //        setMarkerView(MarkerView mv) : 为 chart 设置一个 MarkerView 从而显示选中的值。
        //        getMarkerView() : 获取 chart 已经设置了的 MarkerView ，未设置的话返回 null 。

      /*  需要实现的方法*/
        //        refreshContent(Entry e, Highlight highlight) : 每次 MarkerView 重绘此方法都会被调用，
        //        并为您提供更新它显示的内容的机会（例如，为一个 TextView 设置文本 ，…）。
        //        它提供了当前突出显示的 Entry 和相应的Highlight对象以获得更多信息。
        //        getXOffset(float xpos) : 在这里，应返回要绘制的 MarkerView 在x轴的偏移位置。
        //        默认情况下，MarkerView 的左上边缘处将绘制在 entry 的位置。 在 xpos 参数表示绘制 MarkerView 的默认位置。
        //        getYOffset(float ypos) : 在这里，应返回要绘制的 MarkerView 在y轴的偏移位置。
        //        默认情况下，MarkerView 的左上边缘处将绘制在 entry 的位置。 在 ypos 参数表示绘制MarkerView 的默认位置。

        //        实现自定义的 MarkerView 类后，需要创建一个 .xml 文件来作为 MarkerView 的布局。
        //
    }

    private void animations() {

        //        // 设置动画 lineChart
        //        chart.animateX(8000);
        //        chart.animateY(8000);
        //        chart.animateXY(8000, 8000);
        //        chart.animateY(8000, Easing.EasingOption.EaseInElastic );

        //        barChart
        // 设置动画
        //        chart.animateX(8000); // 图1
        //        chart.animateY(8000); // 图2
        //        chart.animateXY(8000, 8000); // 图3
        //        chart.animateY(8000, Easing.EasingOption.EaseInSine); // 图4

        // 设置动画 pieChart
        //        chart.animateX(8000); // 图1
        //        chart.animateY(8000); // 图2
        //        chart.animateXY(8000,8000); // 图3
        //        chart.animateY(8000, Easing.EasingOption.EaseOutBounce); // 图4


        //        animateX(int durationMillis) : 水平轴的图表值动画，这意味着在指定的时间内从左到右 建立图表。
        //        animateY(int durationMillis) : 垂直轴的图表值动画，这意味着在指定的时间内从下到上 建立图表。
        //        animateXY(int xDuration, int yDuration) : 两个轴的图表值动画，从左到右，从下到上 建立图表。
        //        mChart.animateX(3000); // animate horizontal 3000 milliseconds
        //        // or:
        //        mChart.animateY(3000); // animate vertical 3000 milliseconds
        //        // or:
        //        mChart.animateXY(3000, 3000); // animate horizontal and vertical 3000     milliseconds
        //
        //        任意一种 animate(...) 动画方法被调用后，无需再调用 invalidate() 方法。

    }

    private void resetWindow() {
        //        修改视口的所有方法需要在 为Chart 设置数据之后 调用 。

        //        setVisibleXRangeMinimum(float minXRange) :
        //        设定x轴最大可见区域范围的大小。如果设置为17，则不可能进一步放大视图（在x轴超过17的值）。

        //        setVisibleXRangeMaximum(float maxXRange) :
        //        设定x轴最大可见区域范围的大小。 如果设定为3，则在x轴超过3的值被视为不可见（不滑动 chart 的话）。

        //        setVisibleYRangeMaximum(float maxYRange, AxisDependency axis) :
        //        设定y轴最大可见区域范围的大小。 您还需要提供要对应的轴（YAxis.AxisDependency.LEFT 或 YAxis.AxisDependency.RIGHT )

        //        setViewPortOffsets(float left, float top, float right, float bottom) :
        //        设置当前视图的偏移量（实际图表窗口的两侧偏移量）。 设置这个，将阻止图表自动计算它的偏移量。
        //        使用 resetViewPortOffsets() 撤消此设置。
        //        resetViewPortOffsets() : 撤销所有通过 setViewPortOffsets(...) 方法设置的偏移量 。 允许图表再次自动计算所有偏移。
        //        setExtraOffsets(float left, float top, float right, float bottom) :
        //        设置额外的偏移，将被添加到自动计算的偏移。 这不会改变自动计算的偏移量，但增加了额外的空间给它们。

        //移动视图
        //        fitScreen() : 重设所有缩放和拖动，使图表完全适合它的边界（完全缩小）。
        //        moveViewToX(float xIndex) : 将当前视口的左侧（边）到指定的 x 值。
        //        moveViewToY(float yValue, AxisDependency axis) : 使得指定的y值显示在对应y轴( 左or右 )的中间

        //        moveViewTo(float xIndex, float yValue, AxisDependency axis) :
        //        相当于setVisibleXRange(...) 和 setVisibleYRange(...) 组合使用。
        //        centerViewTo(int xIndex, float yValue, AxisDependency axis) :
        //        相当于 setVisibleXRange(...) 和 setVisibleYRange(...) 组合使用。
        //        注：所有 moveViewTo(...) 方法会自动 invalidate() 刷新图表。 没有必要进行进一步调用 invalidate() 。

        //变焦
        //        zoomIn() : Zooms in by 1.4f, into the charts center.
        //        zoomOut() : Zooms out by 0.7f, from the charts center.
        //        zoom(float scaleX, float scaleY, float x, float y) :
        //        根据所给的参数进行放大或缩小。 参数 x 和 y 是变焦中心的坐标（单位：像素）。 记住，1f = 无放缩 。


    }

    private void dymnamicAddData() {

        //        DataSet 类和所有子类：
        //        addEntry(Entry e) : 添加的 Entry 对象到 DataSet 。

        //        ChartData 类和所有子类：
        //        addEntry(Entry e, int dataSetIndex) : 添加 Entry 到 DataSet 的指定索引处。
        //        addDataSet(DataSet d) : 添加 DataSet 对象到 ChartData 。
        //        除此之外，也有用于动态remove数据的方法。

        //        DataSet 类和所有子类：
        //
        //    public boolean removeFirst() : 删除该 DataSet 的第一个 Entry 项( 索引0 )。 如果成功返回true，否则为false。
        //    public boolean removeLast() : 删除该 DataSet 的最后一个 Entry 项( 索引大小-1 )。 如果成功返回true，否则为false。
        //    public boolean removeEntry(Entry e) : 从 DataSet 中移除给定的 Entry 对象 。 如果成功返回true 。
        //    public boolean removeEntry(int xIndex) : 从 DataSet 中移除给定索引对应的 Entry 对象 。 如果成功返回true 。
        //    ChartData 类和所有子类：
        //
        //    public boolean removeEntry(Entry e, int dataSetIndex) : 移除给定索引对应的 DataSet 中给定的 Entry 对象。如果成功，返回true。
        //    public boolean removeEntry(int xIndex, int dataSetIndex) : 移除给定索引对应的 DataSet 中给定索引对应的 Entry 对象。如果成功，返回true。
        //    public boolean removeDataSet(DataSet d) : 从该 ChartData 对象中移除给定的 DataSet 对象。如果成功，返回true。
        //    public boolean removeDataSet(int index) : 从该 ChartData 对象中移除给定索引对应的 DataSet 对象。如果成功，返回true。

        //        动态添加或移除数据后， 调用invalidate()刷新图表之前 必须调用 notifyDataSetChanged() .


    }


    private void LegendSetting() {
        Legend legend = mChart.getLegend();
        legend.setEnabled(true);
        legend.setWordWrapEnabled(false);
        legend.setMaxSizePercent(2);

        //        自定义legend
        //        setPosition(LegendPosition pos):通过 LegendPosition 设置 Legend 出现的位置。

        //        RIGHT_OF_CHART
        //        RIGHT_OF_CHART_CENTER
        //        RIGHT_OF_CHART_INSIDE
        //        BELOW_CHART_LEFT
        //        BELOW_CHART_RIGHT
        //        BELOW_CHART_CENTER
        //        PIECHART_CENTER（PieChart独有）等等。

        //        setFormSize(float size) : 设置 legend-forms 的大小，单位dp。
        //
        //        setForm(LegendForm shape) :
        //        设置 LegendForm 。This is the shape that is drawn next to the legend-labels with
        //        the color of the DataSet the legend-entry represents. 正方形，圆形或线。


        //        setXEntrySpace(float space) : 设置在水平轴上 legend-entries 的间隙。
        //        setYEntrySpace(float space) : 设置在垂直轴上 legend-entries 的间隙。
        //        setFormToTextSpace(float space) : 设置 legend-form 和 legend-label 之间的空间。
        //        setWordWrapEnabled(boolean enabled) : 设置 Legend 是否自动换行？ 目前仅支持BelowChartLeft，
        //        BelowChartRight，BelowChartCenter。
        //        / you may want to set maxSizePercent when word wrapping, to set the point where the text wraps.


        //自定义标签和颜色。
        //        setCustom(int[] colors, String[] labels) : 设置 自定义Legend 的标签和颜色。 颜色数应该匹配标签数，并且相对应。
        //        A null label will start a group. A (-2) color will avoid drawing a form This will disable the feature
        //        that automatically calculates the legend labels and colors from the datasets. 调用 resetCustom()
        //        重新启用自动计算（and then notifyDataSetChanged() is needed to auto-calculate the legend again）。
        //        resetCustom() : 调用此将禁用通过 setCustom(...)方法自定义的图例标签。在 notifyDataSetChanged() 被调用后，
        //        标签将再次被自动计算。
        //        setExtra(int[] colors, String[] labels) : Sets colors and labels that will be appended to the
        //        end of the auto calculated colors and labels arrays after calculating the legend.
        //        (if the legend has already been calculated, you will need to call notifyDataSetChanged()
        //        to let the changes take effect)


    }

    private void specificChartSetting() {

        //setAutoScaleMinMaxEnabled(boolean enabled) : 标志，指示自动缩放在y轴已启用。
        // 如果启用Y轴自动调整到最小和当前的X轴的范围，只要视口变化的最大y值。
        // 这是图表显示的财务数据特别有趣。 默认值：false
        //柱状图
        /**
         *柱状图
         */
        //setDrawValueAboveBar(boolean enabled) : 如果设置为true，所有值都高于其 bar 的，而不是低于其顶部。默认：true，文字在柱状图的上方
        //        setDrawBarShadow(boolean enabled) :
        // 如果设置为true，会在各条 bar 后面绘制 “灰色全 bar”，用以指示最大值。 启用会降低性能约 40％ 。默认：false
        //用用灰色的补充没有的数值高度

        //        setDrawValuesForWholeStack(boolean enabled) : If set to true, all values of
        // stacked bars are drawn individually, and not just their sum on top of all.
        //如果设置为true，//堆叠条形的所有值都是单独绘制的，而不仅仅是它们的总和
        //        setDrawHighlightArrow(boolean enabled) :Set
        //        this to true to draw the highlightning arrow above each bar when highlighted.
        //        将此设置为true可在突出显示时在每个栏上方绘制突出显示的箭头

        /**
         * 饼状图
         */
        //    setCenterText(SpannableString text) : 设置所绘制在饼图中心的文本。 较长的文本将被自动“wrapped”，
        // 以避免被裁剪成一段一段的。
        //    setCenterTextRadiusPercent(float percent) : 设置中心文本 边框的矩形范围，
        // as a percentage of the pie hole default 1.0f (100%) , 该值可以大于1.0f .
        //    要想真正改变中心文本的大小，要通过 chart.setCenterTextSize(float size); 来进行设置。
        //        chart.setCenterTextSize(float size) : 设置所绘制在饼图中心的文本大小。
        //        setUsePercentValues(boolean enabled) : 如果被启用，在图表内的值绘制在百分之，
        // 而不是与它们的原始值。 规定的值ValueFormatter进行格式化，然后以百分比规定。
        //        setDrawSliceText(boolean enabled) : 设置为true，在扇区绘制x值。

        //        setHoleRadius(float percent) : 设置中心圆孔半径占整个饼状图半径的百分比（100f 是最大=整个图表的半径），
        //        默认的50％的百分比（即50f）。
        //        setTransparentCircleRadius(float percent) : 设置中心透明圈半径占整个饼状图半径的百分比，
        //        默认是 55％ 的半径 -> 大于默认是 50％ 的中心圆孔半径。
        //        setTransparentCircleColor(int color) : 设置透明圈的颜色。
        //        setTransparentCircleAlpha(int alpha) : 设置透明圈的透明度（0-255）。
        //        setRotationAngle(float angle) : 设置饼状图的旋转角度。默认是270f 。


        //        图例
        //        setEnabled(boolean enabled) : 设置Legend启用或禁用。 如果禁用， Legend 将不会被绘制。
        //        setTextColor(int color) : 设置图例标签的颜色。
        //        setTextSize(float size) : 设置在DP传说标签的文字大小。
        //        setTypeface(Typeface tf) : 设置自定义Typeface图例标签。
        //        setWordWrapEnabled(boolean enabled) : 如果启用，Legend 的内容将不会超出图表边界之外，
        //        而是创建一个新的行。 请注意，这会降低性能和仅适用于”legend 位于图表下面”的情况。
        //        setMaxSizePercent(float maxSize) : 设置最大图例的百分比（相对整个图表大小）。 默认值：0.95f（95％）


    }

    private void initSet() {

        //图表的基本设置
        chartBaseSet();

        //手势操作设置
        chartGestureSet();


        //xy轴的基本设置
        chartZhouBase();

        //        X轴和y轴的设置
        XSet();
        YSet();

    }

    private void LineDataSetting() {
        /**
         BarData 类（条形图）
         java.lang.Object
            com.github.mikephil.charting.data.ChartData<T>
                com.github.mikephil.charting.data.BarLineScatterCandleBubbleData<BarDataSet>
                    com.github.mikephil.charting.data.BarData


         setGroupSpace(float percent) : 设置不同 DataSet 的 bar组 之间的间隙（占一个bar的宽度的百分比）。
         如 20f，间隙刚好就是一个bar的宽度的十分之二（下面左图效果）
         如 100f，间隙刚好就是一个bar的宽度（下面右图效果），默认：80

         */

        /**
         * 2. ScatterData 类（散点图）
         * getGreatestShapeSize() : 返回这个data对象中所有 ScatterDataSets 中的最大外形尺寸。
         *
         */

        /**
         *
         * 3. PieData 类（饼状图）

         getDataSet() : 返回已为这个data对象设置了的 PieDataSet 对象。PieData 对象不能包含多个 PieDataSets 对象。
         setDataSet(PieDataSet set) : 为这个data对象设置 PieDataSet 。
         */


        /**
         * BubbleData（原点图）
         * setHighlightCircleWidth(float width) : Sets the width of the circle that surrounds
         * the bubble when in highlighted state for all BubbleDataSet objects this data object contains, in dp.
         */

        /**
         *
         * CombinedData 类 （重点）
         此data对象被设计来包含所有其他数据对象的实例，通过使用此对象提供的数据 setData(...) 方法。
         这个仅用于 CombinedChart 。This data object is designed to contain instances of
         all other data objects. Use the setData(…) methods to provide the data for this object.
         This is used for the CombinedChart only.
         */

        /**
         * CandleDataSet 类(重点）

         setBodySpace(float space) : 设置留出每个烛体，默认0.1F（10％），最大值0.45F，
         最小0F的左侧和右侧的空间。Sets the space that is left out on the left and right side of each candle body, default 0.1f (10%),
         max 0.45f, min 0f
         setShadowWidth(float width) : 设置烛影线的DP的宽度。 默认3F。Sets the width of the candle-shadow-line in dp. Default 3f.
         setShadowColor(int color) : 设置烛影线的颜色。Sets the color of the candle-shadow-line.
         setDecreasingColor(int color) : 设置应该用于此独一无二的颜色DataSet时，开>关闭。
         Sets the one and ONLY color that should be used for this DataSet when open > close.
         setIncreasingColor(int color) : 设置应该用于该数据集的独一无二的颜色，当打开<=关闭。
         Sets the one and ONLY color that should be used for this DataSet when open <= close.
         setDecreasingPaintStyle(Paint.Style style) : 设置绘图方式时开>关闭（填充或描边）。
         Sets paint style when open > close (fill or stroke).
         setIncreasingPaintStyle(Paint.Style style) : 设置绘制风格，当打开<=关闭（填充或描边）。
         Sets paint style when open <= close (fill or stroke).
         *
         */
    }

    private void initData() {
        //在这里初始化数据后，进行设置数据

        //        setDrawValues(boolean enabled) : 启用/禁用 绘制所有 DataSets 数据对象包含的数据的值文本。
        //        setValueTextColor(int color) : 设置 DataSets 数据对象包含的数据的值文本的颜色。
        //        setValueTextSize(float size) : 设置 DataSets 数据对象包含的数据的值文本的大小（单位是dp）。

        //        CombinedData data = new CombinedData();
        //        data.setDrawValues(true);
        //        data.setValueTextColor(R.color.text_green);
        //        data.setValueTextSize(18);

        //      setValueTypeface(Typeface tf) : 设置Typeface的所有价值标签的所有DataSets这些数据对象包含。
        // 设置上面左图的字体,可以在acess目录下房子字体，来进行设置
        //        Typeface tf1 = Typeface.createFromAsset(getAssets(), "OpenSans-BoldItalic.ttf");
        //        dataSet.setValueTypeface(tf1);

        //        getDataSetByIndex(int index) : 返回目标 DataSet 列表中给定索引的数据对象。
        //        contains(Entry entry) : 检查此数据对象是否包含指定的Entry 。 注：这个相当影响性能，性能严峻情况下，不要过度使用。
        //        contains(T dataSet) : Returns true if this data object contains the provided DataSet , false if not.
        //        clearValues() : 清除所有 DataSet 对象和所有 Entries 的数据 。 不会删除所提供的 x-values 。
        //        setHighlightEnabled(boolean enabled) : 设置为true，允许通过点击高亮突出 ChartData 对象和其 DataSets 。

        //        数据的设置

        ArrayList<Entry> al1 = new ArrayList<>();
        ArrayList<Entry> al2 = new ArrayList<>();

        Entry al1e1 = new Entry(100, 0);
        Entry al1e2 = new Entry(200, 1);

        Entry al2e1 = new Entry(300, 2);
        Entry al2e2 = new Entry(400, 3);

        al1.add(al1e1);
        al1.add(al1e2);

        al2.add(al2e1);
        al2.add(al2e2);


        //这是两条线
        LineDataSet linedata1 = new LineDataSet(al1, "line1");
        LineDataSet lineData2 = new LineDataSet(al2, "line2");

        linedata1.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineData2.setAxisDependency(YAxis.AxisDependency.RIGHT);


        List<ILineDataSet> chartdata = new ArrayList<>();
        chartdata.add(linedata1);
        chartdata.add(lineData2);

        ArrayList<String> strings = new ArrayList<>();
        strings.add("q1");
        strings.add("q2");
        strings.add("q3");
        strings.add("q4");

        linedata1.setColors(R.color.text_red);//可以放置一个集合或者数据的颜色
        lineData2.setColor(R.color.text_green);
        LineData datas = new LineData(chartdata);
        datas.setDrawValues(true);
        datas.setValueTextSize(18);
        mChart.setData(datas);
        mChart.invalidate(); // refresh


        //        datas.setValueFormatter(new IValueFormatter() {
        //            @Override
        //            public String getFormattedValue(float value,
        //                                            Entry entry,
        //                                            int dataSetIndex,
        //                                            ViewPortHandler viewPortHandler)
        //            {
        //
        //                //有4中预定义的匹配格式
        ////                LargeValueFormatter：可用于格式化 大于”1.000” 的值。 它会被转变
        ////                PercentFormatter:Used for displaying a “%” sign after each value with 1 decimal digit .显示百分比
        //
        //                return null;
        //            }
        //        });

    }

    private void YSet() {

        //        Y轴 getAxisDependency() 方法以确定此轴表示图表的侧面。
        YAxis leftAxis = mChart.getAxisLeft();
        //setStartAtZero(boolean enabled) : 设置为 true，则无论图表显示的是哪种类型的数据，该轴最小值总是0 。
        //setAxisMaxValue(float max) : 设置该轴的最大值。 如果设置了，这个值将不会是根据提供的数据计算出来的。
        //resetAxisMaxValue() : 调用此方法撤销先前设置的最大值。 通过这样做，你将再次允许轴自动计算出它的最大值。
        //setAxisMinValue(float min) : 设置该轴的自定义最小值。 如果设置了，这个值将不会是根据提供的数据计算出来的。
        //resetAxisMinValue() : 调用此撤销先前设置的最小值。 通过这样做，你将再次允许轴自动计算它的最小值。

        leftAxis.setStartAtZero(true);//该方法过时，用下面的
        leftAxis.setAxisMinimum(23);
        leftAxis.resetAxisMinimum();

        leftAxis.setAxisMaximum(200f);
        leftAxis.resetAxisMaximum();

        //        setInverted(boolean enabled) : 如果设置为true，该轴将被反转，这意味着最高值将在底部，顶部的最低值。
        leftAxis.setInverted(true);
        //      setSpaceTop(float percent) :
        //      设置图表中的最高值的顶部间距占最高值的值的百分比（设置的百分比 = 最高柱顶部间距/最高柱的值）。默认值是10f，即10% 。
        //      setSpaceBottom(float percent) :

        //      setLabelCount(int count, boolean force) :
        //      设置y轴的标签数量。 请注意，这个数字是不固定 if(force == false)，只能是近似的。
        //      如果 if(force == true)，则确切绘制指定数量的标签，但这样可能导致轴线分布不均匀。
        //
        //      setShowOnlyMinMax(boolean enabled) : 如果启用，该轴将只显示它的最小值和最大值。
        //      如果 force == true 这可能会被 忽略/覆盖 。

        //      setPosition(YAxisLabelPosition pos) : 设置，其中轴标签绘制的位置。 无论是 OUTSIDE_CHART 或 INSIDE_CHART 。

        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

        //        setValueFormatter(YAxisValueFormatterf) :
        //        设置该轴的自定义 ValueFormatter 。 该接口允许 格式化/修改 原来的标签文本，返回一个自定义的文本

        //        leftAxis.setValueFormatter(new IAxisValueFormatter() {
        //            @Override
        //            public String getFormattedValue(float value, AxisBase axis) {
        //                return null;
        //            }
        //        });

    }

    private void XSet() {

        //        X轴
        //        自定义轴值
        //        setSpaceBetweenLabels(int characters) : 设置标签字符间的空隙，默认characters间隔是4
        //        setLabelsToSkip(int count) : 设置在”绘制下一个标签”时，要忽略的标签数。
        //        resetLabelsToSkip() : 调用这个方法将使得通过 setLabelsToSkip(...) 的“忽略效果”失效

        //        setAvoidFirstLastClipping(boolean enabled) :
        // 如果设置为true，则在绘制时会避免“剪掉”在x轴上的图表或屏幕边缘的第一个和最后一个坐标轴标签项。
        // setPosition(XAxisPosition pos) : 设置XAxis出现的位置

        //        格式化值
        //        setValueFormatter(XAxisValueFormatter formatter)设置自定义格式，在绘制之前动态调整x的值。

        //        mChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
        //            @Override
        //            public String getFormattedValue(float value, AxisBase axis) {
        //                //用于设置x轴的值样式，预定义了
        //
        ////                DefaultAxisValueFormatter:
        //
        //                return null;
        //            }
        //
        //        });

    }

    private void chartZhouBase() {

        //设置轴启动，下面这些方法适用于两个轴

        // setEnabled(boolean enabled) : 设置轴启用或禁用。如果false，该轴的任何部分都不会被绘制（不绘制坐标轴/便签等）
        // setDrawGridLines(boolean enabled) : 设置为true，则绘制网格线。
        // setDrawAxisLine(boolean enabled) : 设置为true，则绘制该行旁边的轴线（axis-line）。
        // setDrawLabels(boolean enabled) : 设置为true，则绘制轴的标签。
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(Typeface.DEFAULT);
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawLabels(true);


        //        Styling / 修改轴　
        //
        //        setTextColor(int color) : 设置轴标签的颜色。
        //        setTextSize(float size) : 设置轴标签的文字大小。
        //        setTypeface(Typeface tf) : 设置轴标签的 Typeface。
        //        setGridColor(int color) : 设置该轴的网格线颜色。
        //        setGridLineWidth(float width) : 设置该轴网格线的宽度。
        //        setAxisLineColor(int color) : 设置轴线的轴的颜色。
        //        setAxisLineWidth(float width) : 设置该轴轴行的宽度。

        xAxis.setTextColor(R.color.bg_yellow);
        xAxis.setTextSize(12);
        xAxis.setTypeface(Typeface.DEFAULT);
        xAxis.setGridColor(R.color.common_white);
        xAxis.setGridLineWidth(30);
        xAxis.setAxisLineColor(R.color.text_red);
        xAxis.setAxisLineWidth(20);

        xAxis.enableGridDashedLine(3, 2, 1);

        //        enableGridDashedLine(float lineLength, float spaceLength, float phase) :
        //        启用网格线的虚线模式中得出，比如像这样“ - - - - - - ”。
        //
        //        “lineLength”控制虚线段的长度
        //        “spaceLength”控制线之间的空间
        //        “phase”controls the starting point.


        //        限制线
        //
        //两个轴支持 LimitLines 来呈现特定信息，如边界或限制线。LimitLines 加入到 YAxis 在水平方向上绘制，
        // 添加到 XAxis 在垂直方向绘制。 如何通过给定的轴添加和删除 LimitLines：
        //
        //addLimitLine(LimitLine l) : 给该轴添加一个新的 LimitLine 。
        //removeLimitLine(LimitLine l) : 从该轴删除指定 LimitLine 。
        //还有其他的方法进行 添加/删除 操作。

        LimitLine mL = new LimitLine(30f, "这是竖线");
        mL.setLineColor(R.color.text_green);
        mL.setTextColor(R.color.text_orange);
        xAxis.addLimitLine(mL);

        //        setDrawLimitLinesBehindData(boolean enabled) :
        //        设置这条控制线是在实际图形的上面还是下面
    }

    private void chartGestureSet() {

        //        启用/ 禁止 手势交互
        //        setTouchEnabled(boolean enabled) : 启用/禁用与图表的所有可能的触摸交互。
        //        setDragEnabled(boolean enabled) : 启用/禁用拖动（平移）图表。
        //        setScaleEnabled(boolean enabled) : 启用/禁用缩放图表上的两个轴。
        //        setScaleXEnabled(boolean enabled) : 启用/禁用缩放在x轴上。
        //        setScaleYEnabled(boolean enabled) : 启用/禁用缩放在y轴。
        //        setPinchZoom(boolean enabled) : 如果设置为true，捏缩放功能。 如果false，x轴和y轴可分别放大。
        //        setDoubleTapToZoomEnabled(boolean enabled) : 设置为false以禁止通过在其上双击缩放图表。
        //        setHighlightPerDragEnabled(boolean enabled) : 设置为true，允许每个图表表面拖过，当它完全缩小突出。
        // 默认值：true
        //        setHighlightPerTapEnabled(boolean enabled) : 设置为false，以防止值由敲击姿态被突出显示。
        // 值仍然可以通过拖动或编程方式突出显示。 默认值：true

        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setScaleXEnabled(true);
        mChart.setScaleYEnabled(true);
        mChart.setPinchZoom(true);
        mChart.setDoubleTapToZoomEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setHighlightPerDragEnabled(true);


        //        图表的 抛掷/减速
        //        setDragDecelerationEnabled(boolean enabled) : 如果设置为true，手指滑动抛掷图表后继续减速滚动。 默认值：true。
        //        setDragDecelerationFrictionCoef(float coef) : 减速的摩擦系数在[0; 1]区间，数值越高表示速度会缓慢下降，例如，
        //        如果将其设置为0，将立即停止。 1是一个无效的值，会自动转换至0.9999。

        //        高亮  以Java编程方式使得值高亮不会回调 OnChartValueSelectedListener .
        //        highlightValues(Highlight[] highs) : 高亮显示值，高亮显示的点击的位置在数据集中的值。
        // 设置null或空数组则撤消所有高亮。
        //        highlightValue(int xIndex, int dataSetIndex) :
        // 高亮给定xIndex在数据集的值。 设置xIndex或dataSetIndex为-1撤消所有高亮。
        //        getHighlighted() : 返回一个 Highlight[] 其中包含所有高亮对象的信息，xIndex和dataSetIndex。

        mChart.setDragDecelerationEnabled(true);
        mChart.setDragDecelerationFrictionCoef(0.3f);

    }

    private void chartBaseSet() {
        //  设置背景颜色，将覆盖整个图表视图。 此外，背景颜色可以在布局文件 .xml 中进行设置。
        mChart.setBackgroundColor(getResources().getColor(R.color.bg_yellow));

        //设置描述文字与颜色
        Description desc = new Description();
        desc.setText("这是文字");
        desc.setTextColor(R.color.text_blue);

        //自定义描述文字在屏幕上的位置（单位是像素）
        //        mChart.setDescriptionPosition();
        desc.setPosition(999, 999);

        // 设置描述文字的 Typeface
        desc.setTypeface(Typeface.DEFAULT);

        // 设置以像素为单位的描述文字，最小6f，最大16f
        desc.setTextSize(12f);
        mChart.setDescription(desc);

        // 设置当 chart 为空时显示的描述文字。
        mChart.setNoDataText("没有内容，正在加载");

        //        如果启用，chart 绘图区后面的背景矩形将绘制。
        mChart.setDrawGridBackground(true);
        mChart.setGridBackgroundColor(R.color.bg_yellow);

        //        setDrawBorders(boolean enabled) : 启用/禁用绘制图表边框（chart周围的线）。
        //
        //        setBorderColor(int color) : 设置 chart 边框线的颜色。
        //        setBorderWidth(float width) : 设置 chart 边界线的宽度，单位 dp。

        mChart.setDrawBorders(true);
        mChart.setBorderColor(R.color.colorAccent);
        mChart.setBorderWidth(20);

        //        setMaxVisibleValueCount(int count) :
        // 设置最大可见绘制的 chart count 的数量。 只在 setDrawValues() 设置为 true 时有效。
        mChart.setMaxVisibleValueCount(80);
    }


    private void initEvent() {
        //    高亮回调    OnChartValueSelectedListener  以Java编程方式使得值高亮不会回调
        //        OnChartGestureListener 可以使得 chart 与手势操作进行交互。

        mChart.setOnChartValueSelectedListener(this);
        mChart.setOnChartGestureListener(this);
    }


    /**
     * 高亮回调，当值被选中后
     * @param e
     * @param h
     */
    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    /**
     * 没有被选择时
     */
    @Override
    public void onNothingSelected() {

    }


    /**
     * 触摸手势开始时的回调
     * @param me
     * @param lastPerformedGesture
     */
    @Override
    public void onChartGestureStart(MotionEvent me,
                                    ChartTouchListener.ChartGesture lastPerformedGesture)
    {

    }

    /**
     * 触摸手势结束回调
     * @param me
     * @param lastPerformedGesture
     */
    @Override
    public void onChartGestureEnd(MotionEvent me,
                                  ChartTouchListener.ChartGesture lastPerformedGesture)
    {

        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP) {
            mChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
        }
    }

    /**
     * 长按的时候
     * @param me
     */
    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    /**
     * 双击
     * @param me
     */
    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    /**
     * 单击的时候
     * @param me
     */

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    /**
     * 甩动的时候
     * @param me1
     * @param me2
     * @param velocityX
     * @param velocityY
     */
    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    /**
     * 当图表通过双指缩放手势缩放/缩放时的回调
     * @param me
     * @param scaleX
     * @param scaleY
     */
    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    /**
     * 通过拖动手势移动/翻转图表时的回调
     * @param me
     * @param dX
     * @param dY
     */
    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    private void initView() {

//        mChart = (LineChart) findViewById(R.id.combinedChart);
    }
}
