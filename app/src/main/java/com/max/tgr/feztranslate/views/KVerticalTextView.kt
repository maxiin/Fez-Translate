package com.max.tgr.feztranslate.views

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView

class KVerticalTextView: TextView {

    private var topDown:Boolean

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        if (Gravity.isVertical(gravity) && gravity and Gravity.VERTICAL_GRAVITY_MASK == Gravity.BOTTOM) {
            gravity = gravity and Gravity.HORIZONTAL_GRAVITY_MASK or Gravity.TOP
            topDown = false
        } else
            topDown = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec)
        setMeasuredDimension(measuredHeight, measuredWidth)
    }

    override fun onDraw(canvas: Canvas) {

        val textPaint = paint
        textPaint.color = currentTextColor
        textPaint.drawableState = drawableState

        canvas.save()

        if (topDown) {
            canvas.translate(width.toFloat(), 0f)
            canvas.rotate(90f)
        } else {
            canvas.translate(0f, height.toFloat())
            canvas.rotate(-90f)
        }


        canvas.translate(compoundPaddingLeft.toFloat(), extendedPaddingTop.toFloat())

        layout.draw(canvas)
        canvas.restore()
    }

}