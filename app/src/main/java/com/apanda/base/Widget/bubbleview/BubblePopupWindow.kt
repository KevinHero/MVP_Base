package com.apanda.base.Widget.bubbleview

import android.app.Activity
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow

import com.apanda.base.R


/**
 * 气泡弹窗控件
 * 可装入自定义气泡在需要时弹出，不受目标布局的约束

 * Created by cpiz on 2016/8/2.
 */
class BubblePopupWindow
/**
 * 构造函数

 * @param contentView 弹窗内容View，可以是个包裹BubbleView的Layout（方便指定BubbleView的大小），也可以就是一个 BubbleView
 * *
 * @param bubbleView  气泡View
 */
(contentView: View, private val mBubbleView: BubbleStyle?) : PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
    private var mDelayMillis: Long = 0
    private val mHandler = Handler(Looper.getMainLooper())
    private val mDismissRunnable = Runnable { dismiss() }

    init {

        if (mBubbleView == null) {
            throw NullPointerException("Bubble can not be null")
        }
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCanceledOnTouch(true)
    }

    /**
     * 设置点击气泡关闭弹窗

     * @param cancel 是否点击气泡关闭弹窗，默认是
     */
    fun setCanceledOnTouch(cancel: Boolean) {
        contentView.setOnClickListener(if (cancel)
            View.OnClickListener { dismiss() }
        else
            null)
    }

    /**
     * 设置点击外部区域关闭弹窗

     * @param cancel 设置点击外部区域关闭弹窗，默认是
     */
    fun setCanceledOnTouchOutside(cancel: Boolean) {
        isOutsideTouchable = cancel
        isFocusable = cancel
    }

    /**
     * 设置超时后自动关闭弹窗

     * @param delayMillis 自动关闭延时，设0将不会自动关闭
     */
    fun setCanceledOnLater(delayMillis: Long) {
        mHandler.removeCallbacks(mDismissRunnable)
        mDelayMillis = delayMillis
        if (delayMillis > 0) {
            mHandler.postDelayed(mDismissRunnable, delayMillis)
        }
    }

    /**
     * 显示气泡弹窗，并将箭头指向目标

     * @param anchor    气泡箭头要指向的目标
     * *
     * @param direction 箭头方向，同时也决定了气泡出现的位置，不能是 BubbleStyle.ArrowDirection#None
     * *
     * @param offset    气泡箭头与目标的距离
     */
    @JvmOverloads fun showArrowTo(anchor: View, direction: BubbleStyle.ArrowDirection, offset: Int = 0) {
        dismiss()

        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        val navigationBarHeight = getNavigationBarHeight(anchor)
        val anchorRect = getAnchorRectInWindow(anchor)

        mBubbleView.arrowDirection = direction
        contentView.measure(
                View.MeasureSpec.makeMeasureSpec(screenWidth - 2 * MIN_MARGIN, View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(screenHeight - 2 * MIN_MARGIN, View.MeasureSpec.AT_MOST))
        val contentWidth = contentView.measuredWidth
        val contentHeight = contentView.measuredHeight
        Log.d(TAG, String.format("w:%d, h:%d", contentWidth, contentHeight))

        val outProp = PopupProp()
        when (direction) {
            BubbleStyle.ArrowDirection.Left -> getLeftPopupProp(screenWidth, screenHeight, navigationBarHeight, anchorRect, contentWidth, contentHeight, offset, outProp)
            BubbleStyle.ArrowDirection.Right -> getRightPopupProp(screenWidth, screenHeight, navigationBarHeight, anchorRect, contentWidth, contentHeight, offset, outProp)
            BubbleStyle.ArrowDirection.Up -> getUpPopupProp(screenWidth, screenHeight, navigationBarHeight, anchorRect, contentWidth, contentHeight, offset, outProp)
            BubbleStyle.ArrowDirection.Down,
            else -> getDownPopupProp(screenWidth, screenHeight, navigationBarHeight, anchorRect, contentWidth, contentHeight, offset, outProp)
        }

        mBubbleView.arrowDirection = outProp.direction
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        animationStyle = outProp.animationStyle
        if (contentWidth > outProp.maxWidth) {
            width = outProp.maxWidth
        }
        showAtLocation(anchor, outProp.gravity, outProp.x, outProp.y)
        mBubbleView.setArrowTo(anchor)

        if (mDelayMillis > 0) {
            setCanceledOnLater(mDelayMillis)
        }
    }

    private fun getAnchorRectInWindow(anchor: View): Rect {
        val location = IntArray(2)
        anchor.getLocationInWindow(location)
        return Rect(location[0], location[1], location[0] + anchor.width, location[1] + anchor.height)
    }

    private fun getLeftPopupProp(screenWidth: Int, screenHeight: Int,
                                 navigationBarHeight: Int, anchorRect: Rect,
                                 contentWidth: Int, contentHeight: Int,
                                 offset: Int, outProp: PopupProp) {
        outProp.direction = BubbleStyle.ArrowDirection.Left
        outProp.animationStyle = R.style.AnimationArrowLeft
        outProp.maxWidth = screenWidth - anchorRect.right - offset - MIN_MARGIN
        outProp.gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
        outProp.x = anchorRect.right + offset
        outProp.y = anchorRect.centerY() - navigationBarHeight / 2 - screenHeight / 2
    }

    private fun getRightPopupProp(screenWidth: Int, screenHeight: Int,
                                  navigationBarHeight: Int, anchorRect: Rect,
                                  contentWidth: Int, contentHeight: Int,
                                  offset: Int, outProp: PopupProp) {
        outProp.direction = BubbleStyle.ArrowDirection.Right
        outProp.animationStyle = R.style.AnimationArrowRight
        outProp.maxWidth = anchorRect.left - offset - MIN_MARGIN
        outProp.gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
        outProp.x = screenWidth - anchorRect.left + offset
        outProp.y = anchorRect.centerY() - navigationBarHeight / 2 - screenHeight / 2
    }

    private fun getUpPopupProp(screenWidth: Int, screenHeight: Int,
                               navigationBarHeight: Int, anchorRect: Rect,
                               contentWidth: Int, contentHeight: Int,
                               offset: Int, outProp: PopupProp) {
        outProp.direction = BubbleStyle.ArrowDirection.Up
        outProp.animationStyle = R.style.AnimationArrowUp
        outProp.maxWidth = screenWidth - 2 * MIN_MARGIN
        outProp.gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP
        outProp.x = anchorRect.centerX() - screenWidth / 2
        outProp.y = anchorRect.bottom + offset

        if (screenHeight - anchorRect.bottom < contentHeight + offset && anchorRect.top >= contentHeight + offset) {
            getDownPopupProp(screenWidth, screenHeight, navigationBarHeight, anchorRect, contentWidth, contentHeight, offset, outProp)
        }
    }

    private fun getDownPopupProp(screenWidth: Int, screenHeight: Int,
                                 navigationBarHeight: Int, anchorRect: Rect,
                                 contentWidth: Int, contentHeight: Int,
                                 offset: Int, outProp: PopupProp) {
        outProp.direction = BubbleStyle.ArrowDirection.Down
        outProp.animationStyle = R.style.AnimationArrowDown
        outProp.maxWidth = screenWidth - 2 * MIN_MARGIN
        outProp.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
        outProp.x = anchorRect.centerX() - screenWidth / 2
        outProp.y = screenHeight + navigationBarHeight - anchorRect.top + offset

        if (anchorRect.top < contentHeight + offset && screenHeight - anchorRect.bottom >= contentHeight + offset) {
            getUpPopupProp(screenWidth, screenHeight, navigationBarHeight, anchorRect, contentWidth, contentHeight, offset, outProp)
        }
    }

    internal inner class PopupProp {
        var direction: BubbleStyle.ArrowDirection? = null
        var animationStyle: Int = 0
        var maxWidth: Int = 0
        var gravity: Int = 0
        var x: Int = 0
        var y: Int = 0
    }

    companion object {
        private val TAG = "BubblePopupWindow"
        private val MIN_MARGIN = BubbleImpl.dpToPx(2)

        /**
         * 获得用于补偿位置偏移的 NavigationBar 高度
         * 在 Android5.0 以上系统，showAtLocation 如果使用了 Gravity.BOTTOM 或 Gravity.CENTER_VERTICAL 可能出现显示偏移的Bug
         * 偏移值和 NavigationBar 高度有关

         * @param anchorView 目标View
         * *
         * @return 如果需要修正且存在NavigationBar则返回高度，否则为0
         */
        private fun getNavigationBarHeight(anchorView: View): Int {
            if (anchorView.rootView.context is Activity) {
                val activity = anchorView.rootView.context as Activity
                val flags = activity.window.attributes.flags
                if (flags and WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS != 0) { // 没有这个属性无须修正
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                        val defaultDisplay = activity.windowManager.defaultDisplay
                        val metrics = DisplayMetrics()
                        defaultDisplay.getMetrics(metrics)
                        val usableHeight = metrics.heightPixels
                        defaultDisplay.getRealMetrics(metrics) // getRealMetrics is only available with API 17 and +
                        val realHeight = metrics.heightPixels
                        return if (realHeight > usableHeight) realHeight - usableHeight else 0
                    }
                }
            }

            return 0
        }
    }
}
/**
 * 显示气泡弹窗，并将箭头指向目标

 * @param anchor    气泡箭头要指向的目标
 * *
 * @param direction 箭头方向，同时也决定了气泡出现的位置，因此不能是 BubbleStyle.ArrowDirection#None
 */
