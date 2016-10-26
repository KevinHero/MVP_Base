package com.apanda.base.Widget.bubbleview

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.RectF
import android.graphics.drawable.Drawable

/**
 * 气泡框背景

 * Created by caijw on 2016/5/26.
 * https://github.com/cpiz/BubbleView
 */
internal class BubbleDrawable : Drawable() {
    private var mArrowDirection: BubbleStyle.ArrowDirection = BubbleStyle.ArrowDirection.None
    private val mOriginalShape = Shape()
    private val mBorderShape = Shape()
    private val mFillShape = Shape()
    private val mBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mBorderPath = Path()
    private val mFillPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mFillPath = Path()
    private var mFillPadding = 0f
    private var mFillColor = 0xCC000000.toInt()
    private var mBorderColor = Color.WHITE
    private val mArrowTo = PointF(0f, 0f)

    private inner class Shape {
        internal var Rect = RectF()
        internal var BorderWidth = 0f
        internal var ArrowHeight = 0f
        internal var ArrowWidth = 0f
        internal var ArrowOffset = 0f
        internal var ArrowPeakX = 0f
        internal var ArrowPeakY = 0f
        internal var TopLeftRadius = 0f
        internal var TopRightRadius = 0f
        internal var BottomLeftRadius = 0f
        internal var BottomRightRadius = 0f

        internal fun set(shape: Shape) {
            this.Rect.set(shape.Rect)
            this.BorderWidth = shape.BorderWidth
            this.ArrowHeight = shape.ArrowHeight
            this.ArrowWidth = shape.ArrowWidth
            this.ArrowOffset = shape.ArrowOffset
            this.ArrowPeakX = shape.ArrowPeakX
            this.ArrowPeakY = shape.ArrowPeakY
            this.TopLeftRadius = shape.TopLeftRadius
            this.TopRightRadius = shape.TopRightRadius
            this.BottomLeftRadius = shape.BottomLeftRadius
            this.BottomRightRadius = shape.BottomRightRadius
        }
    }

    fun resetRect(width: Int, height: Int) {
        mOriginalShape.Rect.set(0f, 0f, width.toFloat(), height.toFloat())
    }

    fun setFillColor(fillColor: Int) {
        mFillColor = fillColor
    }

    fun setBorderColor(borderColor: Int) {
        mBorderColor = borderColor
    }

    fun setBorderWidth(borderWidth: Float) {
        mOriginalShape.BorderWidth = borderWidth
    }

    fun setFillPadding(fillPadding: Float) {
        mFillPadding = fillPadding
    }

    fun rebuildShapes() {
        buildBorderShape()
        buildFillShape()
    }

    private fun buildBorderShape() {
        // 预留四周1/2的边框厚度，使得边框能够完全显示
        mBorderShape.set(mOriginalShape)
        mBorderShape.Rect.set(
                mOriginalShape.Rect.left + mOriginalShape.BorderWidth / 2 + if (mArrowDirection.isLeft) mOriginalShape.ArrowHeight else 0,
                mOriginalShape.Rect.top + mOriginalShape.BorderWidth / 2 + if (mArrowDirection.isUp) mOriginalShape.ArrowHeight else 0,
                mOriginalShape.Rect.right - mOriginalShape.BorderWidth / 2 - if (mArrowDirection.isRight) mOriginalShape.ArrowHeight else 0,
                mOriginalShape.Rect.bottom - mOriginalShape.BorderWidth / 2 - if (mArrowDirection.isDown) mOriginalShape.ArrowHeight else 0)
        buildArrowPeak(mArrowDirection, mBorderShape)

        mBorderPath.reset()
        buildPath(mBorderShape, mBorderPath)
    }

    private fun buildFillShape() {
        mFillShape.set(mBorderShape)
        mFillShape.BorderWidth = 0f
        mFillShape.Rect.set(
                mOriginalShape.Rect.left + mOriginalShape.BorderWidth + mFillPadding + if (mArrowDirection.isLeft) mOriginalShape.ArrowHeight else 0,
                mOriginalShape.Rect.top + mOriginalShape.BorderWidth + mFillPadding + if (mArrowDirection.isUp) mOriginalShape.ArrowHeight else 0,
                mOriginalShape.Rect.right - mOriginalShape.BorderWidth - mFillPadding - if (mArrowDirection.isRight) mOriginalShape.ArrowHeight else 0,
                mOriginalShape.Rect.bottom - mOriginalShape.BorderWidth - mFillPadding - if (mArrowDirection.isDown) mOriginalShape.ArrowHeight else 0)
        mFillShape.TopLeftRadius = Math.max(0f, mOriginalShape.TopLeftRadius - mOriginalShape.BorderWidth / 2 - mFillPadding)
        mFillShape.TopRightRadius = Math.max(0f, mOriginalShape.TopRightRadius - mOriginalShape.BorderWidth / 2 - mFillPadding)
        mFillShape.BottomLeftRadius = Math.max(0f, mOriginalShape.BottomLeftRadius - mOriginalShape.BorderWidth / 2 - mFillPadding)
        mFillShape.BottomRightRadius = Math.max(0f, mOriginalShape.BottomRightRadius - mOriginalShape.BorderWidth / 2 - mFillPadding)

        val w = mOriginalShape.ArrowWidth - 2 * (mOriginalShape.BorderWidth / 2 + mFillPadding) / Math.sin(Math.atan((mOriginalShape.ArrowHeight / (mOriginalShape.ArrowWidth / 2)).toDouble()))
        val h = w * mOriginalShape.ArrowHeight / mOriginalShape.ArrowWidth

        mFillShape.ArrowHeight = (h + (mOriginalShape.BorderWidth / 2).toDouble() + mFillPadding.toDouble()).toFloat()
        mFillShape.ArrowWidth = mFillShape.ArrowHeight * mOriginalShape.ArrowWidth / mOriginalShape.ArrowHeight
        buildArrowPeak(mArrowDirection, mFillShape)

        mFillPath.reset()
        buildPath(mFillShape, mFillPath)
    }

    private fun buildArrowPeak(direction: BubbleStyle.ArrowDirection, shape: Shape) {
        when (direction) {
            BubbleStyle.ArrowDirection.Left -> buildLeftArrowPeak(shape)
            BubbleStyle.ArrowDirection.Up -> buildUpArrowPeak(shape)
            BubbleStyle.ArrowDirection.Right -> buildRightArrowPeak(shape)
            BubbleStyle.ArrowDirection.Down -> buildDownArrowPeak(shape)
        }
    }

    fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
        mOriginalShape.TopLeftRadius = topLeft
        mOriginalShape.TopRightRadius = topRight
        mOriginalShape.BottomRightRadius = bottomRight
        mOriginalShape.BottomLeftRadius = bottomLeft
    }

    fun setArrowDirection(arrowDirection: BubbleStyle.ArrowDirection) {
        mArrowDirection = arrowDirection
    }

    fun setArrowHeight(arrowHeight: Float) {
        mOriginalShape.ArrowHeight = arrowHeight
    }

    fun setArrowWidth(arrowWidth: Float) {
        mOriginalShape.ArrowWidth = arrowWidth
    }

    /**
     * 设置箭头指向的View对象中心相对坐标

     * @param x 目标中心x
     * *
     * @param y 目标中心y
     */
    fun setArrowTo(x: Float, y: Float) {
        mArrowTo.x = x
        mArrowTo.y = y
    }

    fun setArrowPos(arrowPos: Float) {
        mOriginalShape.ArrowOffset = arrowPos
    }

    override fun draw(canvas: Canvas) {
        mFillPaint.style = Paint.Style.FILL
        mFillPaint.color = mFillColor
        canvas.drawPath(mFillPath, mFillPaint)

        if (mBorderShape.BorderWidth > 0) {
            mBorderPaint.style = Paint.Style.STROKE
            mBorderPaint.strokeCap = Paint.Cap.ROUND
            mBorderPaint.strokeJoin = Paint.Join.ROUND
            mBorderPaint.strokeWidth = mBorderShape.BorderWidth
            mBorderPaint.color = mBorderColor
            canvas.drawPath(mBorderPath, mBorderPaint)
        }
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    override fun getOpacity(): Int {
        return 0
    }

    private fun buildPath(shape: Shape, path: Path) {
        when (mArrowDirection) {
            BubbleStyle.ArrowDirection.None -> buildWithNoneArrow(shape, path)
            BubbleStyle.ArrowDirection.Up -> buildWithUpArrow(shape, path)
            BubbleStyle.ArrowDirection.Down -> buildWithDownArrow(shape, path)
            BubbleStyle.ArrowDirection.Left -> buildWithLeftArrow(shape, path)
            BubbleStyle.ArrowDirection.Right -> buildWithRightArrow(shape, path)
        }
    }

    private fun buildWithNoneArrow(shape: Shape, path: Path) {
        val rect = shape.Rect
        path.moveTo(rect.left, rect.top + shape.TopLeftRadius)
        compatPathArcTo(path, rect.left, rect.top,
                rect.left + 2 * shape.TopLeftRadius, rect.top + 2 * shape.TopLeftRadius, 180f, 90f)
        path.lineTo(rect.right - shape.TopRightRadius, rect.top)
        buildTopRightCorner(shape, path)
        path.lineTo(rect.right, rect.bottom - shape.BottomRightRadius)
        buildBottomRightCorner(shape, path)
        path.lineTo(rect.left + shape.BottomLeftRadius, rect.bottom)
        buildBottomLeftCorner(shape, path)
        path.lineTo(rect.left, rect.top + shape.TopLeftRadius)
    }

    private fun buildWithLeftArrow(shape: Shape, path: Path) {
        val rect = shape.Rect
        path.moveTo(shape.ArrowPeakX, shape.ArrowPeakY) // 从箭头顶点开始沿顺时针方向绘制
        path.lineTo(rect.left, shape.ArrowPeakY - shape.ArrowWidth / 2)
        path.lineTo(rect.left, rect.top + shape.TopLeftRadius) // 左上竖线
        buildTopLeftCorner(shape, path) // 左上弧角
        path.lineTo(rect.right - shape.TopRightRadius, rect.top) // 上横线
        buildTopRightCorner(shape, path) // 右上弧角
        path.lineTo(rect.right, rect.bottom - shape.BottomRightRadius) // 右侧竖线
        buildBottomRightCorner(shape, path) // 右下弧角
        path.lineTo(rect.left + shape.BottomLeftRadius, rect.bottom) // 底部横向
        buildBottomLeftCorner(shape, path) // 左下弧角
        path.lineTo(rect.left, shape.ArrowPeakY + shape.ArrowWidth / 2) // 左下竖线
        path.lineTo(shape.ArrowPeakX, shape.ArrowPeakY) // 回到顶点
    }

    private fun buildWithUpArrow(shape: Shape, path: Path) {
        val rect = shape.Rect
        path.moveTo(shape.ArrowPeakX, shape.ArrowPeakY)
        path.lineTo(shape.ArrowPeakX + shape.ArrowWidth / 2, rect.top)
        path.lineTo(rect.right - shape.TopRightRadius, rect.top)
        buildTopRightCorner(shape, path)
        path.lineTo(rect.right, rect.bottom - shape.BottomRightRadius)
        buildBottomRightCorner(shape, path)
        path.lineTo(rect.left + shape.BottomLeftRadius, rect.bottom)
        buildBottomLeftCorner(shape, path)
        path.lineTo(rect.left, rect.top + shape.TopLeftRadius)
        buildTopLeftCorner(shape, path)
        path.lineTo(shape.ArrowPeakX - shape.ArrowWidth / 2, rect.top)
        path.lineTo(shape.ArrowPeakX, shape.ArrowPeakY)
    }

    private fun buildWithRightArrow(shape: Shape, path: Path) {
        val rect = shape.Rect
        path.moveTo(shape.ArrowPeakX, shape.ArrowPeakY)
        path.lineTo(rect.right, shape.ArrowPeakY + shape.ArrowWidth / 2)
        path.lineTo(rect.right, rect.bottom - shape.BottomRightRadius)
        buildBottomRightCorner(shape, path)
        path.lineTo(rect.left + shape.BottomLeftRadius, rect.bottom)
        buildBottomLeftCorner(shape, path)
        path.lineTo(rect.left, rect.top + shape.TopLeftRadius)
        buildTopLeftCorner(shape, path)
        path.lineTo(rect.right - shape.TopRightRadius, rect.top)
        buildTopRightCorner(shape, path)
        path.lineTo(rect.right, shape.ArrowPeakY - shape.ArrowWidth / 2)
        path.lineTo(shape.ArrowPeakX, shape.ArrowPeakY)
    }

    private fun buildWithDownArrow(shape: Shape, path: Path) {
        val rect = shape.Rect
        path.moveTo(shape.ArrowPeakX, shape.ArrowPeakY)
        path.lineTo(shape.ArrowPeakX - shape.ArrowWidth / 2, rect.bottom)
        path.lineTo(rect.left + shape.BottomLeftRadius, rect.bottom)
        buildBottomLeftCorner(shape, path)
        path.lineTo(rect.left, rect.top + shape.TopLeftRadius)
        buildTopLeftCorner(shape, path)
        path.lineTo(rect.right - shape.TopRightRadius, rect.top)
        buildTopRightCorner(shape, path)
        path.lineTo(rect.right, rect.bottom - shape.BottomRightRadius)
        buildBottomRightCorner(shape, path)
        path.lineTo(shape.ArrowPeakX + shape.ArrowWidth / 2, rect.bottom)
        path.lineTo(shape.ArrowPeakX, shape.ArrowPeakY)
    }

    private fun buildLeftArrowPeak(shape: Shape) {
        val y: Float
        if (mArrowTo.x == 0f && mArrowTo.y == 0f && shape.ArrowOffset != 0f) {
            y = shape.ArrowOffset + if (shape.ArrowOffset > 0) 0 else shape.Rect.bottom + shape.Rect.top
        } else {
            y = shape.Rect.centerY() + mArrowTo.y
        }

        shape.ArrowPeakX = shape.Rect.left - shape.ArrowHeight
        shape.ArrowPeakY = bound(shape.Rect.top + shape.TopLeftRadius + shape.ArrowWidth / 2 + shape.BorderWidth / 2,
                y, // 确保弧角的显示
                shape.Rect.bottom - shape.BottomLeftRadius - shape.ArrowWidth / 2 - shape.BorderWidth / 2)
        shape.ArrowOffset = shape.ArrowPeakY
    }

    private fun buildUpArrowPeak(shape: Shape) {
        val x: Float
        if (mArrowTo.x == 0f && mArrowTo.y == 0f && shape.ArrowOffset != 0f) {
            x = shape.ArrowOffset + if (shape.ArrowOffset > 0) 0 else shape.Rect.right + shape.Rect.left
        } else {
            x = shape.Rect.centerX() + mArrowTo.x
        }

        shape.ArrowPeakX = bound(shape.Rect.left + shape.TopLeftRadius + shape.ArrowWidth / 2 + shape.BorderWidth / 2,
                x,
                shape.Rect.right - shape.TopRightRadius - shape.ArrowWidth / 2 - shape.BorderWidth / 2)
        shape.ArrowPeakY = shape.Rect.top - shape.ArrowHeight
        shape.ArrowOffset = shape.ArrowPeakX
    }

    private fun buildDownArrowPeak(shape: Shape) {
        val x: Float
        if (mArrowTo.x == 0f && mArrowTo.y == 0f && shape.ArrowOffset != 0f) {
            x = shape.ArrowOffset + if (shape.ArrowOffset > 0) 0 else shape.Rect.right + shape.Rect.left
        } else {
            x = shape.Rect.centerX() + mArrowTo.x
        }

        shape.ArrowPeakX = bound(shape.Rect.left + shape.BottomLeftRadius + shape.ArrowWidth / 2 + shape.BorderWidth / 2,
                x,
                shape.Rect.right - shape.BottomRightRadius - shape.ArrowWidth / 2 - shape.BorderWidth / 2)
        shape.ArrowPeakY = shape.Rect.bottom + shape.ArrowHeight
        shape.ArrowOffset = shape.ArrowPeakX
    }

    private fun buildRightArrowPeak(shape: Shape) {
        val y: Float
        if (mArrowTo.x == 0f && mArrowTo.y == 0f && shape.ArrowOffset != 0f) {
            y = shape.ArrowOffset + if (shape.ArrowOffset > 0) 0 else shape.Rect.bottom + shape.Rect.top
        } else {
            y = shape.Rect.centerY() + mArrowTo.y
        }

        shape.ArrowPeakX = shape.Rect.right + shape.ArrowHeight
        shape.ArrowPeakY = bound(shape.Rect.top + shape.TopRightRadius + shape.ArrowWidth / 2 + shape.BorderWidth / 2,
                y,
                shape.Rect.bottom - shape.BottomRightRadius - shape.ArrowWidth / 2 - shape.BorderWidth / 2)
        shape.ArrowOffset = shape.ArrowPeakY
    }

    private fun buildTopLeftCorner(shape: Shape, path: Path) {
        compatPathArcTo(path,
                shape.Rect.left,
                shape.Rect.top,
                shape.Rect.left + 2 * shape.TopLeftRadius,
                shape.Rect.top + 2 * shape.TopLeftRadius,
                180f,
                90f)
    }

    private fun buildTopRightCorner(shape: Shape, path: Path) {
        compatPathArcTo(path,
                shape.Rect.right - 2 * shape.TopRightRadius,
                shape.Rect.top,
                shape.Rect.right,
                shape.Rect.top + 2 * shape.TopRightRadius,
                270f,
                90f)
    }

    private fun buildBottomRightCorner(shape: Shape, path: Path) {
        compatPathArcTo(path,
                shape.Rect.right - 2 * shape.BottomRightRadius,
                shape.Rect.bottom - 2 * shape.BottomRightRadius,
                shape.Rect.right,
                shape.Rect.bottom,
                0f,
                90f)
    }

    private fun buildBottomLeftCorner(shape: Shape, path: Path) {
        compatPathArcTo(path,
                shape.Rect.left,
                shape.Rect.bottom - 2 * shape.BottomLeftRadius,
                shape.Rect.left + 2 * shape.BottomLeftRadius,
                shape.Rect.bottom,
                90f,
                90f)
    }

    private val mOvalRect = RectF()

    fun compatPathArcTo(path: Path,
                        left: Float,
                        top: Float,
                        right: Float,
                        bottom: Float,
                        startAngle: Float,
                        sweepAngle: Float) {
        mOvalRect.set(left, top, right, bottom)
        path.arcTo(mOvalRect, startAngle, sweepAngle)
    }

    private fun bound(min: Float, `val`: Float, max: Float): Float {
        return Math.min(Math.max(`val`, min), max)
    }
}
