package com.khapp.suji.ui

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.khapp.suji.R
import com.khapp.suji.utils.ScreenUtils
import com.khapp.suji.utils.Utils
import kotlin.math.max

import kotlin.math.min


/**
 * 直方图，需要的数据集：[X轴，Y轴]
 */
class HistogramView : View {
    //柱状图属性
    var space = 30f
    var itemSpace = 10f
    var titleSpace = 32f
    var itemWidth = 20f
    var radius = itemWidth / 2

    var textSize = 36f
    var textColor = Color.BLACK

    // 网格图属性
    var dashWidth = 10f
    var dashGap = 10f
    var gridWidth = 4f
    var gridLines = 4

    var dataSet = ArrayList<Histogram>()
    var grid: HistogramGrid? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.HistogramView, defStyle, 0
        )

        itemSpace = a.getDimension(
            R.styleable.HistogramView_itemSpace,
            itemSpace
        )
        titleSpace = a.getDimension(
            R.styleable.HistogramView_titleSpace,
            titleSpace
        )
        itemWidth = a.getDimension(
            R.styleable.HistogramView_itemWidth,
            itemWidth
        )
        textSize = a.getDimension(
            R.styleable.HistogramView_textSize,
            textSize
        )
        textColor = a.getColor(R.styleable.HistogramView_textColor, textColor)
        dashGap = a.getDimension(
            R.styleable.HistogramView_dashGap,
            dashGap
        )
        dashWidth = a.getDimension(
            R.styleable.HistogramView_dashWidth,
            dashWidth
        )
        gridWidth = a.getDimension(
            R.styleable.HistogramView_gridWidth,
            gridWidth
        )
        radius = a.getDimension(
            R.styleable.HistogramView_radius,
            radius
        )
        gridLines = a.getInteger(
            R.styleable.HistogramView_gridLines,
            gridLines
        )
        a.recycle()


    }

    private fun getWeight(data: ArrayList<Histogram.HistogramData>): Float {

        return getMax(data) / (height.toFloat() / 1.5f)
    }

    private fun getMax(data: ArrayList<Histogram.HistogramData>): Float {
        var max = 0f
        data.forEach {
            max = max(it.data.max()!!, max)
        }
        return max
    }

    fun setData(data: ArrayList<Histogram.HistogramData>, itemColors: Array<Int>) {
        dataSet.clear()
        grid = null
        val startX = 115f
        val startY = height - 50f

        val histogram = Histogram(startX, startY, data[0]).let { h ->
            h.itemSpace = itemSpace
            h.itemWidth = itemWidth
            h.textSize = textSize
            h.titleSpace = titleSpace
            h
        }
        space = (width - startX * 3 / 2 - histogram.getWidth() * data.size) / (data.size - 1)
        val weight = getWeight(data)
        grid = HistogramGrid(
            startX,
            startY - histogram.textHeight - titleSpace,
            height.toFloat(),
            width.toFloat() - startX / 2,
            weight
        ).let { hg ->
            hg.dashWidth = dashWidth
            hg.dashGap = dashGap
            hg.gridWidth = gridWidth
            hg.textSize = textSize
            hg.textColor = textColor
            hg.gridColor = textColor
            hg.lineSize = gridLines
            hg
        }
        data.forEachIndexed { index, histogramData ->

            dataSet.add(
                Histogram(
                    startX + (histogram.getWidth() + space) * index,
                    startY,
                    histogramData
                ).let { h ->
                    h.itemSpace = itemSpace
                    h.itemWidth = itemWidth
                    h.textColor = textColor
                    h.textSize = textSize
                    h.titleSpace = titleSpace
                    h.itemColors = itemColors
                    h.radius = radius
                    h.weight = weight
                    h
                })
        }
        invalidate()
    }

    fun test() {
        setData(
            arrayListOf(
                HistogramView.Histogram.HistogramData("周日", arrayListOf(2020f, 200f)),
                HistogramView.Histogram.HistogramData("周一", arrayListOf(300f, 200f)),
                HistogramView.Histogram.HistogramData("周二", arrayListOf(1450f, 270f)),
                HistogramView.Histogram.HistogramData("周三", arrayListOf(140f, 1100f)),
                HistogramView.Histogram.HistogramData("周四", arrayListOf(1430f, 220f)),
                HistogramView.Histogram.HistogramData("周五", arrayListOf(1440f, 1120f)),
                HistogramView.Histogram.HistogramData("周六", arrayListOf(730f, 1220f))
            ),
            arrayOf(Color.parseColor("#FF5D5D"), Color.parseColor("#5EDA72"))
        )
    }

    private fun getSize(measureSpec: Int, isWidth: Boolean): Int {
        val result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        result = when (specMode) {
            MeasureSpec.EXACTLY -> {
                specSize //确切大小,所以将得到的尺寸给view
            }
            MeasureSpec.AT_MOST -> {
                //默认值为450px,此处要结合父控件给子控件的最多大小(要不然会填充父控件),所以采用最小值
                min(if (isWidth) width else height, specSize)
            }
            else -> {
                if (isWidth) width else height
            }
        }
        return result
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(getSize(widthMeasureSpec, true), getSize(heightMeasureSpec, false))
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        test()
        grid?.draw(canvas)
        dataSet.forEach {
            it.draw(canvas)
        }

    }

    class HistogramGrid(
        var x: Float,
        var y: Float,
        var height: Float,
        var width: Float,
        var weight: Float
    ) {

        var dotLinePaint = Paint()
        var gridColor = Color.GRAY
            set(value) {
                field = value
                invalidatePaint()
            }

        var gridWidth = 4f
            set(value) {
                field = value
                invalidatePaint()
            }
        var dashGap = 10f
            set(value) {
                field = value
                invalidatePaint()
            }
        var dashWidth = 10f
            set(value) {
                field = value
                invalidatePaint()
            }
        var lineSize = 4
            set(value) {
                field = value
                invalidatePaint()
            }
        var linePaths = ArrayList<Path>()
        var labels = ArrayList<LabelText>()

        var textPaint = TextPaint()
        var textColor = Color.GRAY
            set(value) {
                field = value
                invalidatePaint()
            }
        var textSize = 36f
            set(value) {
                field = value
                invalidatePaint()
            }

        fun invalidatePaint() {
            dotLinePaint.apply {
                color = gridColor
                strokeWidth = gridWidth
                pathEffect = DashPathEffect(floatArrayOf(dashWidth, dashGap), 0f)
            }
            textPaint.let {
                it.textSize = textSize
                it.color = textColor
            }
            linePaths.clear()
            labels.clear()
            val lineSpace = (height - 50) / lineSize
            val scale = lineSpace * weight
            for (i in 0..lineSize) {

                val lineY = y - lineSpace * i
                labels.add(
                    LabelText(
                        x, lineY,
                        Utils.formatChartScale(scale * i), textPaint
                    )
                )

                val path = Path()
                path.moveTo(x, lineY)
                path.lineTo(width, lineY)
                linePaths.add(path)
            }
        }

        init {
            dotLinePaint.apply {
                isAntiAlias = true
                style = Paint.Style.STROKE
            }
            textPaint.isAntiAlias = true
            invalidatePaint()
        }

        fun draw(canvas: Canvas) {
            linePaths.forEach {
                canvas.drawPath(it, dotLinePaint)
            }
            labels.forEach {
                it.draw(canvas)
            }
        }

        class LabelText(
            val x: Float,
            val y: Float,
            val text: String,
            private val textPaint: TextPaint
        ) {
            var textWidth: Float = 0f
            var textHeight = 0f
            var textMargin = 16f

            init {
                textWidth = textPaint.measureText(text)
                textHeight = textPaint.fontMetrics.bottom * 2
            }

            fun draw(canvas: Canvas) {
                canvas.drawText(text, x - textWidth - textMargin, y + textHeight / 2, textPaint)
            }
        }

    }

    class Histogram(private var x: Float, private var y: Float, var data: HistogramData) {
        var paints = arrayListOf<Paint>()
        var itemColors: Array<Int> = arrayOf(Color.BLUE)
            set(value) {
                field = value
                invalidateData()
            }
        var titleSpace = 32f
            set(value) {
                field = value
                invalidateData()
            }
        var itemSpace = 10f
            set(value) {
                field = value
                invalidateData()
            }
        var textPaint = TextPaint()
        var textWidth: Float = 0f
        var textHeight: Float = 0f

        var weight: Float = 1.0f
            set(value) {
                field = value
                invalidateData()
            }
        var itemWidth: Float = 20f
            set(value) {
                field = value
                invalidateData()
            }

        var textColor = Color.BLACK
            set(value) {
                field = value
                invalidateData()
            }
        var textSize: Float = 48f
            set(value) {
                field = value
                invalidateData()
            }

        val dataRect = ArrayList<DataRect>()

        init {
            paints.clear()
            data.data.forEach { _ ->
                paints.add(Paint().apply {
                    isAntiAlias = true
                    style = Paint.Style.FILL
                })
            }
            textPaint.let {
                it.flags = Paint.ANTI_ALIAS_FLAG
                it.textAlign = Paint.Align.CENTER
            }
            invalidateData()
        }

        private fun invalidateData() {
            itemColors.forEachIndexed { index, itemColor ->
                paints[index].color = itemColor
            }
            textPaint.let {
                it.textSize = textSize
                it.color = textColor
                textWidth = it.measureText(data.title)
                textHeight = it.fontMetrics.bottom * 2
            }
            dataRect.clear()
            data.data.forEachIndexed { index, fl ->
                dataRect.add(
                    DataRect(
                        getDataSetX() + index * (itemWidth + itemSpace),
                        y - textHeight - titleSpace,
                        itemWidth,
                        fl / weight
                    )
                )
            }
        }


        fun draw(canvas: Canvas) {


            canvas.drawText(
                data.title,
                x + getWidth() / 2,
                y,
                textPaint
            )
            dataRect.forEachIndexed { index, dataRect ->
                canvas.drawRoundRect(
                    dataRect.left,
                    dataRect.top,
                    dataRect.right,
                    dataRect.bottom,
                    radius,
                    radius,
                    paints[index]
                )
            }
//            canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), Paint().apply {
//                style = Paint.Style.STROKE
//                strokeWidth = 1f
//                color = Color.BLACK
//            })
        }

        fun getDataSetWidth(): Float {
            return (itemWidth * data.data.size) + (itemSpace * (data.data.size - 1))
        }

        fun getHeight(): Float {
            return data.data.max()!! / weight + textHeight + titleSpace
        }

        fun getWidth(): Float {
            return max(
                textWidth,
                getDataSetWidth()
            )
        }

        fun getDataSetX(): Float {
            return x + (getWidth() - getDataSetWidth()) / 2
        }

        var radius = itemWidth / 2
            set(value) {
                field = value
                invalidateData()
            }


        fun getLeft(): Float {
            return x
        }

        fun getRight(): Float {
            return x + getWidth()
        }

        fun getTop(): Float {
            return y - getHeight()
        }

        fun getBottom(): Float {
            return y
        }

        data class HistogramData(val title: String, val data: ArrayList<Float>)
        data class DataRect(val x: Float, val y: Float, val width: Float, val height: Float) {
            var left: Float = 0f
                get() {
                    return x
                }
            var right: Float = 0f
                get() {
                    return x + width
                }
            var top: Float = 0f
                get() {
                    return y - height
                }
            var bottom: Float = 0f
                get() {
                    return y
                }
        }
    }


}