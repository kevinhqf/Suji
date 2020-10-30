package com.khapp.suji.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.khapp.suji.R
import com.khapp.suji.utils.ScreenUtils
import kotlin.math.min

class SlideView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.SlideView)
        bgColor = ta.getColor(R.styleable.SlideView_bgColor, Color.parseColor("#211E2A"))
        textColor = ta.getColor(R.styleable.SlideView_txtColor, Color.WHITE)
        text = ta.getString(R.styleable.SlideView_txt)?:""

        bgPaint.color = bgColor
        textPaint.color = textColor
    }

    var onSlideListener: OnSlideListener? = null
    private var radius = 0f
    private var mWidth = 800
    private var mHeight = 150
    private var bgPaint: Paint
    private var bgColor = Color.parseColor("#211E2A")
    private val slideIcon = SlideIcon()
    private var text = "滑动"
    private var textPaint: Paint
    private var textSize = 50f
    private var textColor = Color.WHITE
    var baseline = 0f
    private var iconX = 0f
        set(value) {
            slideIcon.x = value
            field = value
            invalidate()
        }
        get() {
            return slideIcon.x
        }
    private var slideFinished = false

    init {


        bgPaint = Paint()
        bgPaint.style = Paint.Style.FILL
        bgPaint.isAntiAlias = true
        textPaint = Paint()
        textPaint.textSize = textSize
        textPaint.style = Paint.Style.FILL
        textPaint.isAntiAlias = true
        textPaint.isFakeBoldText = true
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.isElegantTextHeight = true


        mWidth = ScreenUtils.getWidth(context)
        mHeight = ((slideIcon.radius * 2).toInt() + slideIcon.radius).toInt()
        radius = (mHeight / 2).toFloat()
        iconX = slideIcon.radius / 2
        val fontMetrics = textPaint.fontMetrics
        val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val rectF = RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat())
        baseline = rectF.centerY() + distance
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = getSize(widthMeasureSpec, true)
        mHeight = getSize(heightMeasureSpec, false)
        setMeasuredDimension(mWidth, mHeight)
        slideIcon.y = mHeight / 2 - slideIcon.radius

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRoundRect(0f, 0f, mWidth.toFloat(), mHeight.toFloat(), radius, radius, bgPaint)
        canvas?.drawText(text, (mWidth / 2).toFloat(), baseline, textPaint)
        slideIcon.draw(canvas)
    }

    var startX = 0f
    var startY = 0f
    var endX = 0f
    var isPress = false
    override fun onTouchEvent(event: MotionEvent?): Boolean {


        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
                isPress = slideIcon.isTouch(startX, startY)
            }
            MotionEvent.ACTION_MOVE -> {
                if (isPress && !slideFinished && (event.x > (slideIcon.x + slideIcon.radius) && (event.x < (mWidth - slideIcon.radius * 1.5)))) {
                    onSlideListener?.onSlide()
                    val deltaX = event.x - slideIcon.x
                    slideIcon.x += deltaX - slideIcon.radius
                    invalidate()
                }

            }
            MotionEvent.ACTION_UP -> {
                isPress = false
                endX = event.x
                if (!slideFinished) {
                    if (endX > mWidth / 2) {
                        slideToRight()
                        slideFinished = true
                        onSlideListener?.onSlideFinished()
                    } else {
                        slideToLeft()
                    }
                }

            }
        }
        return true
    }

    fun reset() {
        iconX = slideIcon.radius / 2
        slideFinished = false
    }

    private fun slideToLeft() {
        val animator = ObjectAnimator.ofFloat(this, "iconX", slideIcon.x, slideIcon.radius / 2)
        animator.duration = 200
        animator.start()
    }

    private fun slideToRight() {
        val animator = ObjectAnimator.ofFloat(this, "iconX", slideIcon.x, (mWidth - slideIcon.radius * 2.5f))
        animator.duration = 200
        animator.start()
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
                min(if (isWidth) mWidth else mHeight, specSize)
            }
            else -> {
                if (isWidth) mWidth else mHeight
            }
        }
        return result
    }

    interface OnSlideListener {
        fun onSlideFinished()
        fun onSlide()

    }

    class SlideIcon {
        var margin = 0
        var x = 0f

        var y = 0f
        var radius = 45f
        var bgPaint = Paint()
        var strokePaint = Paint()
        var bgColor = "#ffffff"
        var color = "#211E2A"

        var left: Float = 0.0f
            get() {
                return x
            }
        var right = 0f
            get() {
                return x + radius * 2
            }
        var top = 0f
            get() {
                return y
            }
        var bottom = 0f
            get() {
                return y + radius * 2
            }
        val path = Path()

        init {
            bgPaint.isAntiAlias = true
            bgPaint.style = Paint.Style.FILL
            bgPaint.color = Color.parseColor(bgColor)
            strokePaint.style = Paint.Style.STROKE
            strokePaint.color = Color.parseColor(color)
            strokePaint.strokeWidth = 10f

        }

        fun isTouch(x: Float, y: Float): Boolean {
            return (x > left && x < right && y > top && y < bottom)
        }

        fun draw(canvas: Canvas?) {
            canvas?.drawCircle(x + radius + margin, y + radius, radius, bgPaint)
            path.reset()
            path.moveTo(x + radius / 2 + 15 + margin, y + radius / 2)
            path.lineTo(x + radius + 15 + margin, y + radius)
            path.lineTo(x + radius / 2 + 15 + margin, y + radius + radius / 2)
            canvas?.drawPath(path, strokePaint)
        }
    }
}