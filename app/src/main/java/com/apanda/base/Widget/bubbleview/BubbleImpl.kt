package com.apanda.base.Widget.bubbleview

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View

import com.apanda.base.R

import java.lang.ref.WeakReference

/**
 * 气泡控件的实现类，将与真正的气泡View进行聚合，方便扩展
 *
 *
 * Created by caijw on 2016/6/1.
 * https://github.com/cpiz/BubbleView
 */
internal class BubbleImpl : BubbleStyle {
    private var mParentView: View? = null
    private var mHolderCallback: BubbleCallback? = null
    private val mBubbleDrawable = BubbleDrawable()
    private var mArrowDirection: BubbleStyle.ArrowDirection = BubbleStyle.ArrowDirection.None
    private var mArrowToViewRef: WeakReference<View>? = null
    private var mArrowToViewId = 0
    private var mArrowHeight = 0f
    private var mArrowWidth = 0f
    private var mArrowOffset = 0f
    override var cornerTopLeftRadius = 0f
        private set
    override var cornerTopRightRadius = 0f
        private set
    override var cornerBottomLeftRadius = 0f
        private set
    override var cornerBottomRightRadius = 0f
        private set
    private var mPaddingLeftOffset = 0
    private var mPaddingTopOffset = 0
    private var mPaddingRightOffset = 0
    private var mPaddingBottomOffset = 0
    private var mFillColor = 0xCC000000.toInt()
    private var mBorderColor = Color.WHITE
    private var mBorderWidth = 0f
    private var mFillPadding = 0f
    private val mOnLayoutChangeListener = View.OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom -> updateDrawable() }

    fun init(view: View, context: Context, attrs: AttributeSet?) {
        mParentView = view
        mHolderCallback = view as BubbleCallback

        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.BubbleStyle)
            mArrowDirection = BubbleStyle.ArrowDirection.valueOf(ta.getInt(R.styleable.BubbleStyle_bb_arrowDirection, 0))
            mArrowHeight = ta.getDimension(R.styleable.BubbleStyle_bb_arrowHeight, dpToPx(6).toFloat())
            mArrowWidth = ta.getDimension(R.styleable.BubbleStyle_bb_arrowWidth, dpToPx(10).toFloat())
            mArrowOffset = ta.getDimension(R.styleable.BubbleStyle_bb_arrowOffset, 0f)
            mArrowToViewId = ta.getResourceId(R.styleable.BubbleStyle_bb_arrowTo, 0)

            val radius = ta.getDimension(R.styleable.BubbleStyle_bb_cornerRadius, dpToPx(4).toFloat())
            cornerTopLeftRadius = cornerTopRightRadius = cornerBottomLeftRadius = cornerBottomRightRadius = radius
            cornerTopLeftRadius = ta.getDimension(R.styleable.BubbleStyle_bb_cornerTopLeftRadius, cornerTopLeftRadius)
            cornerTopRightRadius = ta.getDimension(R.styleable.BubbleStyle_bb_cornerTopRightRadius, cornerTopLeftRadius)
            cornerBottomLeftRadius = ta.getDimension(R.styleable.BubbleStyle_bb_cornerBottomLeftRadius, cornerTopLeftRadius)
            cornerBottomRightRadius = ta.getDimension(R.styleable.BubbleStyle_bb_cornerBottomRightRadius, cornerTopLeftRadius)

            mFillColor = ta.getColor(R.styleable.BubbleStyle_bb_fillColor, 0xCC000000.toInt())
            mFillPadding = ta.getDimension(R.styleable.BubbleStyle_bb_fillPadding, 0f)
            mBorderColor = ta.getColor(R.styleable.BubbleStyle_bb_borderColor, Color.WHITE)
            mBorderWidth = ta.getDimension(R.styleable.BubbleStyle_bb_borderWidth, 0f)
            ta.recycle()
        }
        updateDrawable(mParentView!!.width, mParentView!!.height, false)
    }

    /**
     * 设置箭头朝向

     * @param arrowDirection 上下左右或者无
     */
    override var arrowDirection: BubbleStyle.ArrowDirection
        get() = mArrowDirection
        set(arrowDirection) {
            mArrowDirection = arrowDirection
            updateDrawable()
        }

    /**
     * 设置箭头三角形厚度

     * @param arrowHeight 箭头厚度
     */
    override var arrowHeight: Float
        get() = mArrowHeight
        set(arrowHeight) {
            mArrowHeight = arrowHeight
            updateDrawable()
        }

    /**
     * 设置箭头三角形底宽

     * @param arrowWidth 箭头底边宽度
     */
    override var arrowWidth: Float
        get() = mArrowWidth
        set(arrowWidth) {
            mArrowWidth = arrowWidth
            updateDrawable()
        }

    /**
     * 设置箭头在边线上的位置，视箭头方向而定

     * @param arrowOffset 根据箭头位置，偏移像素值：
     * *                    朝上/下时在X轴方向偏移，>0 时从正方向偏移，<0时从负方向偏移
     * *                    朝左/右时在Y轴方向偏移，>0 时从正方向偏移，<0时从负方向偏移
     */
    override var arrowOffset: Float
        get() = mArrowOffset
        set(arrowOffset) {
            mArrowOffset = arrowOffset
            updateDrawable()
        }

    /**
     * 设置箭头指向的View对象ID
     * 设置了View对象后，setArrowPos将不起作用

     * @param targetViewId 指向的ViewId
     */
    override fun setArrowTo(targetViewId: Int) {
        mArrowToViewId = targetViewId
        setArrowToRef(null) // 先不设置，在updateDrawable会重新寻找
        updateDrawable()
    }

    override fun setArrowTo(targetView: View?) {
        mArrowToViewId = if (targetView != null) targetView.id else 0
        setArrowToRef(targetView)
        updateDrawable()
    }

    override val arrowTo: View?
        get() = if (mArrowToViewRef != null) mArrowToViewRef!!.get() else null

    /**
     * 设置气泡背景色

     * @param fillColor 气泡背景颜色
     */
    override var fillColor: Int
        get() = mFillColor
        set(fillColor) {
            mFillColor = fillColor
            updateDrawable()
        }

    /**
     * 设置边框线颜色

     * @param borderColor 边框颜色
     */
    override var borderColor: Int
        get() = mBorderColor
        set(borderColor) {
            mBorderColor = borderColor
            updateDrawable()
        }

    /**
     * 设置边框线宽

     * @param borderWidth 边框厚度
     */
    override var borderWidth: Float
        get() = mBorderWidth
        set(borderWidth) {
            mBorderWidth = borderWidth
            updateDrawable()
        }

    /**
     * 设置边框于背景之间的间隙宽度

     * @param fillPadding 间隙宽度
     */
    override var fillPadding: Float
        get() = mFillPadding
        set(fillPadding) {
            mFillPadding = fillPadding
            updateDrawable()
        }

    /**
     * 设置边角弧度
     * 可以为四角指定不同弧度

     * @param topLeft     左上角
     * *
     * @param topRight    右上角
     * *
     * @param bottomRight 右下角
     * *
     * @param bottomLeft  左下角
     */
    override fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
        cornerTopLeftRadius = topLeft
        cornerTopRightRadius = topRight
        cornerBottomRightRadius = bottomRight
        cornerBottomLeftRadius = bottomLeft
        updateDrawable()
    }

    override fun setCornerRadius(radius: Float) {
        setCornerRadius(radius, radius, radius, radius)
    }

    @SuppressWarnings("SuspiciousNameCombination")
    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        if (mHolderCallback == null) {
            return
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            val stack = Throwable().getStackTrace()
            for (i in 0..6) {
                if (stack[i].getClassName() == View::class.java.name && stack[i].getMethodName() == "recomputePadding") {
                    Log.w("BubbleImpl", "Called setPadding by View on old Android platform")
                    mHolderCallback!!.setSuperPadding(left, top, right, bottom)
                    return
                }
            }
        }

        mPaddingLeftOffset = mPaddingTopOffset = mPaddingRightOffset = mPaddingBottomOffset = 0
        when (mArrowDirection) {
            BubbleStyle.ArrowDirection.Left -> mPaddingLeftOffset += mArrowHeight.toInt()
            BubbleStyle.ArrowDirection.Up -> mPaddingTopOffset += mArrowHeight.toInt()
            BubbleStyle.ArrowDirection.Right -> mPaddingRightOffset += mArrowHeight.toInt()
            BubbleStyle.ArrowDirection.Down -> mPaddingBottomOffset += mArrowHeight.toInt()
        }

        mHolderCallback!!.setSuperPadding(
                left + mPaddingLeftOffset,
                top + mPaddingTopOffset,
                right + mPaddingRightOffset,
                bottom + mPaddingBottomOffset)
    }

    override val paddingLeft: Int
        get() = mHolderCallback!!.superPaddingLeft - mPaddingLeftOffset

    override val paddingTop: Int
        get() = mHolderCallback!!.superPaddingTop - mPaddingTopOffset

    override val paddingRight: Int
        get() = mHolderCallback!!.superPaddingRight - mPaddingRightOffset

    override val paddingBottom: Int
        get() = mHolderCallback!!.superPaddingBottom - mPaddingBottomOffset

    // 方便计算用的中间值对象，避免重复创建
    private val mLocation = IntArray(2)
    private val mRectTo = Rect()
    private val mRectSelf = Rect()

    fun updateDrawable(width: Int, height: Int, drawImmediately: Boolean) {
        var arrowToOffsetX = 0
        var arrowToOffsetY = 0

        var arrowToView = arrowTo
        if (arrowToView == null && mArrowToViewId != 0) {
            arrowToView = findGlobalViewById(mArrowToViewId)
            setArrowToRef(arrowToView)
        }

        if (arrowToView != null) {
            arrowToView.getLocationOnScreen(mLocation)
            mRectTo.set(mLocation[0], mLocation[1],
                    mLocation[0] + arrowToView.width, mLocation[1] + arrowToView.height)

            mParentView!!.getLocationOnScreen(mLocation)
            mRectSelf.set(mLocation[0], mLocation[1], mLocation[0] + width, mLocation[1] + height)

            arrowToOffsetX = mRectTo.centerX() - mRectSelf.centerX()
            arrowToOffsetY = mRectTo.centerY() - mRectSelf.centerY()

            mArrowDirection = getAutoArrowDirection(width, height, arrowToOffsetX, arrowToOffsetY, mArrowHeight.toInt())
        }
        setPadding(mParentView!!.paddingLeft, mParentView!!.paddingTop, mParentView!!.paddingRight, mParentView!!.paddingBottom)

        if (drawImmediately) {
            mBubbleDrawable.resetRect(width, height)
            mBubbleDrawable.setCornerRadius(cornerTopLeftRadius, cornerTopRightRadius, cornerBottomRightRadius, cornerBottomLeftRadius)
            mBubbleDrawable.setFillColor(mFillColor)
            mBubbleDrawable.setBorderWidth(mBorderWidth)
            mBubbleDrawable.setFillPadding(mFillPadding)
            mBubbleDrawable.setBorderColor(mBorderColor)
            mBubbleDrawable.setArrowDirection(mArrowDirection)
            mBubbleDrawable.setArrowTo(arrowToOffsetX.toFloat(), arrowToOffsetY.toFloat())
            mBubbleDrawable.setArrowPos(mArrowOffset)
            mBubbleDrawable.setArrowHeight(mArrowHeight)
            mBubbleDrawable.setArrowWidth(mArrowWidth)
            mBubbleDrawable.rebuildShapes()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mParentView!!.background = mBubbleDrawable
            } else {
                // noinspection deprecation
                mParentView!!.setBackgroundDrawable(mBubbleDrawable)
            }
        }
    }

    override fun updateDrawable() {
        updateDrawable(mParentView!!.width, mParentView!!.height, true)
    }

    private fun findGlobalViewById(viewId: Int): View? {
        if (viewId == 0) {
            return null
        }

        var vp: View = mParentView
        while (vp.parent is View) {
            // 逐层在父View中查找，是为了查找离自己最近的目标对象，因为ID可能重复
            vp = vp.parent as View
            val arrowToView = vp.findViewById(viewId)
            if (arrowToView != null) {
                return arrowToView
            }
        }

        return null
    }

    private fun setArrowToRef(targetView: View?) {
        if (mArrowToViewRef != null) {
            val oldTargetView = mArrowToViewRef!!.get()
            oldTargetView?.removeOnLayoutChangeListener(mOnLayoutChangeListener)
        }

        mArrowToViewRef = if (targetView != null) WeakReference(targetView) else null
        targetView?.addOnLayoutChangeListener(mOnLayoutChangeListener)
    }

    /**
     * 根据目标对象相对中心位置，推导箭头朝向

     * @param width   自己的宽度
     * *
     * @param height  自己的高度
     * *
     * @param offsetX 目标对象中心相对X
     * *
     * @param offsetY 目标对象中心相对Y
     * *
     * @return 推导出的箭头朝向
     */
    private fun getAutoArrowDirection(width: Int, height: Int, offsetX: Int, offsetY: Int, arrowHeight: Int): BubbleStyle.ArrowDirection {
        val targetCenterX = offsetX + width / 2
        val targetCenterY = offsetY + height / 2

        if (targetCenterX < arrowHeight && targetCenterY > 0 && targetCenterY < height) {
            return BubbleStyle.ArrowDirection.Left
        } else if (targetCenterY < arrowHeight && targetCenterX > 0 && targetCenterX < width) {
            return BubbleStyle.ArrowDirection.Up
        } else if (targetCenterX > width - arrowHeight && targetCenterY > 0 && targetCenterY < height) {
            return BubbleStyle.ArrowDirection.Right
        } else if (targetCenterY > height - arrowHeight && targetCenterX > 0 && targetCenterX < width) {
            return BubbleStyle.ArrowDirection.Down
        } else if (Math.abs(offsetX) > Math.abs(offsetY) && offsetX < 0) {
            return BubbleStyle.ArrowDirection.Left
        } else if (Math.abs(offsetX) < Math.abs(offsetY) && offsetY < 0) {
            return BubbleStyle.ArrowDirection.Up
        } else if (Math.abs(offsetX) > Math.abs(offsetY) && offsetX > 0) {
            return BubbleStyle.ArrowDirection.Right
        } else if (Math.abs(offsetX) < Math.abs(offsetY) && offsetY > 0) {
            return BubbleStyle.ArrowDirection.Down
        } else {
            return BubbleStyle.ArrowDirection.None
        }
    }

    companion object {

        /**
         * dp转为px

         * @param dp dp值
         * *
         * @return px值
         */
        fun dpToPx(dp: Int): Int {
            return (dp * Resources.getSystem().displayMetrics.density).toInt()
        }
    }
}
