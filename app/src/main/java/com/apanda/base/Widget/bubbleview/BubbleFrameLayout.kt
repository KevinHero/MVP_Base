package com.apanda.base.Widget.bubbleview

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout

/**
 * 气泡样式的FrameLayout布局
 * 支持自定义气泡样式

 * Created by caijw on 2016/5/26.
 * https://github.com/cpiz/BubbleView
 */
@SuppressWarnings("unused")
class BubbleFrameLayout : FrameLayout, BubbleStyle, BubbleCallback {
    private val mBubbleImpl = BubbleImpl()

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        mBubbleImpl.init(this, context, attrs)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            mBubbleImpl.updateDrawable(right - left, bottom - top, true)
        }
    }

    override var arrowDirection: BubbleStyle.ArrowDirection
        get() = mBubbleImpl.arrowDirection
        set(arrowDirection) {
            mBubbleImpl.arrowDirection = arrowDirection
        }

    override var arrowHeight: Float
        get() = mBubbleImpl.arrowHeight
        set(arrowHeight) {
            mBubbleImpl.arrowHeight = arrowHeight
        }

    override var arrowWidth: Float
        get() = mBubbleImpl.arrowWidth
        set(arrowWidth) {
            mBubbleImpl.arrowWidth = arrowWidth
        }

    override var arrowOffset: Float
        get() = mBubbleImpl.arrowOffset
        set(arrowOffset) {
            mBubbleImpl.arrowOffset = arrowOffset
        }

    override fun setArrowTo(viewId: Int) {
        mBubbleImpl.setArrowTo(viewId)
    }

    override fun setArrowTo(view: View) {
        mBubbleImpl.arrowTo = view
    }

    override val arrowTo: View
        get() = mBubbleImpl.arrowTo

    override var fillColor: Int
        get() = mBubbleImpl.fillColor
        set(fillColor) {
            mBubbleImpl.fillColor = fillColor
        }

    override var borderColor: Int
        get() = mBubbleImpl.borderColor
        set(borderColor) {
            mBubbleImpl.borderColor = borderColor
        }

    override var borderWidth: Float
        get() = mBubbleImpl.borderWidth
        set(borderWidth) {
            mBubbleImpl.borderWidth = borderWidth
        }

    override var fillPadding: Float
        get() = mBubbleImpl.fillPadding
        set(fillPadding) {
            mBubbleImpl.fillPadding = fillPadding
        }

    override fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
        mBubbleImpl.setCornerRadius(topLeft, topRight, bottomRight, bottomLeft)
    }

    override fun setCornerRadius(radius: Float) {
        mBubbleImpl.setCornerRadius(radius)
    }

    override val cornerTopLeftRadius: Float
        get() = mBubbleImpl.cornerTopLeftRadius

    override val cornerTopRightRadius: Float
        get() = mBubbleImpl.cornerTopRightRadius

    override val cornerBottomLeftRadius: Float
        get() = mBubbleImpl.cornerBottomLeftRadius

    override val cornerBottomRightRadius: Float
        get() = mBubbleImpl.cornerBottomRightRadius

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        if (mBubbleImpl == null) {
            Log.w("BubbleView", "mBubbleImpl == null on old Android platform")
            setSuperPadding(left, top, right, bottom)
            return
        }

        mBubbleImpl.setPadding(left, top, right, bottom)
    }

    override fun getPaddingLeft(): Int {
        return mBubbleImpl.paddingLeft
    }

    override fun getPaddingTop(): Int {
        return mBubbleImpl.paddingTop
    }

    override fun getPaddingRight(): Int {
        return mBubbleImpl.paddingRight
    }

    override fun getPaddingBottom(): Int {
        return mBubbleImpl.paddingBottom
    }

    override fun setSuperPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
    }

    override val superPaddingLeft: Int
        get() = super.getPaddingLeft()

    override val superPaddingTop: Int
        get() = super.getPaddingTop()

    override val superPaddingRight: Int
        get() = super.getPaddingRight()

    override val superPaddingBottom: Int
        get() = super.getPaddingBottom()

    override fun updateDrawable() {
        mBubbleImpl.updateDrawable()
    }
}
