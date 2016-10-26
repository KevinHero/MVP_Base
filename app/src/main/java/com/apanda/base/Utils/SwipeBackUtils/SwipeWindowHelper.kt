package com.apanda.base.Utils.SwipeBackUtils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout

import com.apanda.base.base.BaseApplication


/**
 * Created by Android on 2016/10/12.
 */

class SwipeWindowHelper @JvmOverloads constructor(private val mCurrentWindow: Window?, private var mIsSupportSlideBack: Boolean = true //
) : Handler() {

    private var mIsSliding: Boolean = false //是否正在滑动
    private var mIsSlideAnimPlaying: Boolean = false //滑动动画展示过程中
    private var mDistanceX: Float = 0.toFloat()  //px 当前滑动距离 （正数或0）
    private var mMarginThreshold: Int = 0  //px 拦截手势区间
    private var mLastPointX: Float = 0.toFloat()  //记录手势在屏幕上的X轴坐标
    private val mViewManager: ViewManager
    private val mCurrentContentView: FrameLayout

    init {
        mCurrentContentView = getContentView(mCurrentWindow)
        mViewManager = ViewManager()
    }

    fun setSupportSlideBack(supportSlideBack: Boolean) {
        mIsSupportSlideBack = supportSlideBack
    }

    fun processTouchEvent(ev: MotionEvent): Boolean {
        if (!mIsSupportSlideBack) { //不支持滑动返回，则手势事件交给View处理
            return false
        }

        if (mIsSlideAnimPlaying) {  //正在滑动动画播放中，直接消费手势事件
            return true
        }

        if (mMarginThreshold == 0) {  //动态设置滑动拦截事件的区域
            val commonMargin = 45
            mMarginThreshold = Math.min(MARGIN_THRESHOLD, commonMargin)
        }

        val action = ev.action and MotionEvent.ACTION_MASK
        val actionIndex = ev.actionIndex

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mLastPointX = ev.rawX
                val inThresholdArea = mLastPointX >= 0 && mLastPointX <= mMarginThreshold

                if (inThresholdArea && mIsSliding) {
                    return true
                } else if (inThresholdArea && !mIsSliding) { //开始滑动
                    mIsSliding = true
                    sendEmptyMessage(MSG_ACTION_DOWN)
                    return true
                }
            }

            MotionEvent.ACTION_POINTER_DOWN -> if (mIsSliding) {  //有第二个手势事件加入，而且正在滑动事件中，则直接消费事件
                return true
            }

            MotionEvent.ACTION_MOVE -> if (mIsSliding && actionIndex == 0) { //开始滑动
                val message = obtainMessage()
                val bundle = Bundle()
                bundle.putFloat(CURRENT_POINT_X, ev.rawX)
                message.what = MSG_ACTION_MOVE
                message.data = bundle
                sendMessage(message)
                return true
            } else if (mIsSliding && actionIndex != 0) {
                return true
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_OUTSIDE -> if (mIsSliding && actionIndex == 0) { // 取消滑动 或 手势抬起 ，而且手势事件是第一手势，开始滑动动画
                mIsSliding = false
                sendEmptyMessage(MSG_ACTION_UP)
                return true
            } else if (mIsSliding && actionIndex != 0) {
                return true
            }
            else -> mIsSliding = false
        }
        return false
    }

    val context: Context?
        get() = mCurrentWindow?.context

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        when (msg.what) {
            MSG_ACTION_DOWN -> {
                // hide input method
                val inputMethod = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val view = mCurrentWindow!!.currentFocus
                if (view != null) {
                    inputMethod.hideSoftInputFromWindow(view.windowToken, 0)
                }

                if (!mViewManager.addViewFromPreviousActivity()) return

                // add shadow view on the left of content view
                mViewManager.addShadowView()

                if (mCurrentContentView.childCount >= 3) {
                    val curView = mViewManager.displayView
                    if (curView.background == null) {
                        val color = windowBackgroundColor
                        curView.setBackgroundColor(color)
                    }
                }
            }

            MSG_ACTION_MOVE -> {
                val curPointX = msg.data.getFloat(CURRENT_POINT_X)
                onSliding(curPointX)
            }

            MSG_ACTION_UP -> {
                val width = context!!.resources.displayMetrics.widthPixels
                if (mDistanceX == 0f) {
                    if (mCurrentContentView.childCount >= 3) {
                        mViewManager.removeShadowView()
                        mViewManager.resetPreviousView()
                    }
                } else if (mDistanceX > width / 4) {
                    sendEmptyMessage(MSG_SLIDE_PROCEED)
                } else {
                    sendEmptyMessage(MSG_SLIDE_CANCEL)
                }
            }

            MSG_SLIDE_CANCEL -> startSlideAnim(true)

            MSG_SLIDE_CANCELED -> {
                mDistanceX = 0f
                mIsSliding = false
                mViewManager.removeShadowView()
                mViewManager.resetPreviousView()
            }

            MSG_SLIDE_PROCEED -> startSlideAnim(false)

            MSG_SLIDE_FINISHED -> {
                mViewManager.addCacheView()
                mViewManager.removeShadowView()
                mViewManager.resetPreviousView()

                if (context is Activity) {
                    val activity = context as Activity?
                    activity!!.finish()
                    activity.overridePendingTransition(0, 0)
                } else if (context is ContextWrapper) {
                    val baseContext = (context as ContextWrapper).baseContext
                    if (baseContext is Activity) {
                        baseContext.finish()
                        baseContext.overridePendingTransition(0, 0)
                    }
                }
            }

            else -> {
            }
        }
    }

    private val windowBackgroundColor: Int
        get() {
            var array: TypedArray? = null
            try {
                array = context!!.theme.obtainStyledAttributes(intArrayOf(android.R.attr.windowBackground))
                return array!!.getColor(0, ContextCompat.getColor(context!!, android.R.color.transparent))
            } finally {
                if (array != null) {
                    array.recycle()
                }
            }
        }

    /**
     * 手动处理滑动事件
     */
    @Synchronized private fun onSliding(curPointX: Float) {
        val width = context!!.resources.displayMetrics.widthPixels
        val previewActivityContentView = mViewManager.mPreviousContentView
        val shadowView = mViewManager.mShadowView
        val currentActivityContentView = mViewManager.displayView

        if (previewActivityContentView == null || currentActivityContentView == null || shadowView == null) {
            sendEmptyMessage(MSG_SLIDE_CANCELED)
            return
        }

        val distanceX = curPointX - mLastPointX
        mLastPointX = curPointX
        mDistanceX = mDistanceX + distanceX
        if (mDistanceX < 0) {
            mDistanceX = 0f
        }

        previewActivityContentView.x = -width / 3 + mDistanceX / 3
        shadowView.x = mDistanceX - SHADOW_WIDTH
        currentActivityContentView.x = mDistanceX
    }

    /**
     * 开始自动滑动动画

     * @param slideCanceled 是不是要返回（true则不关闭当前页面）
     */
    private fun startSlideAnim(slideCanceled: Boolean) {
        val previewView = mViewManager.mPreviousContentView
        val shadowView = mViewManager.mShadowView
        val currentView = mViewManager.displayView

        if (previewView == null || currentView == null) {
            return
        }

        val width = context!!.resources.displayMetrics.widthPixels
        val interpolator = DecelerateInterpolator(2f)

        // preview activity's animation
        val previewViewAnim = ObjectAnimator()
        previewViewAnim.interpolator = interpolator
        previewViewAnim.setProperty(View.TRANSLATION_X)
        val preViewStart = mDistanceX / 3 - width / 3
        val preViewStop = (if (slideCanceled) -width / 3 else 0).toFloat()
        previewViewAnim.setFloatValues(preViewStart, preViewStop)
        previewViewAnim.target = previewView

        // shadow view's animation
        val shadowViewAnim = ObjectAnimator()
        shadowViewAnim.interpolator = interpolator
        shadowViewAnim.setProperty(View.TRANSLATION_X)
        val shadowViewStart = mDistanceX - SHADOW_WIDTH
        val shadowViewEnd = (if (slideCanceled) SHADOW_WIDTH else width + SHADOW_WIDTH).toFloat()
        shadowViewAnim.setFloatValues(shadowViewStart, shadowViewEnd)
        shadowViewAnim.target = shadowView

        // current view's animation
        val currentViewAnim = ObjectAnimator()
        currentViewAnim.interpolator = interpolator
        currentViewAnim.setProperty(View.TRANSLATION_X)
        val curViewStart = mDistanceX
        val curViewStop = (if (slideCanceled) 0 else width).toFloat()
        currentViewAnim.setFloatValues(curViewStart, curViewStop)
        currentViewAnim.target = currentView

        // play animation together
        val animatorSet = AnimatorSet()
        animatorSet.duration = (if (slideCanceled) 150 else 300).toLong()
        animatorSet.playTogether(previewViewAnim, shadowViewAnim, currentViewAnim)
        animatorSet.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                if (slideCanceled) {
                    mIsSlideAnimPlaying = false
                    previewView.x = 0f
                    shadowView!!.x = (-SHADOW_WIDTH).toFloat()
                    currentView.x = 0f
                    sendEmptyMessage(MSG_SLIDE_CANCELED)
                } else {
                    sendEmptyMessage(MSG_SLIDE_FINISHED)
                }
            }
        })
        animatorSet.start()
        mIsSlideAnimPlaying = true
    }


    private fun getContentView(window: Window?): FrameLayout? {
        if (window == null) return null
        return window.findViewById(Window.ID_ANDROID_CONTENT) as FrameLayout
    }

    internal inner class ViewManager {
        private var mPreviousActivity: Activity? = null
        private var mPreviousContentView: ViewGroup? = null
        private var mShadowView: View? = null

        /**
         * Remove view from previous Activity and add into current Activity

         * @return Is view added successfully
         */
        private fun addViewFromPreviousActivity(): Boolean {
            if (mCurrentContentView.childCount == 0) {
                mPreviousActivity = null
                mPreviousContentView = null
                return false
            }

            val application = mCurrentWindow!!.context.applicationContext as BaseApplication
            mPreviousActivity = application.activityLifecycleHelper.preActivity
            if (mPreviousActivity == null) {
                mPreviousActivity = null
                mPreviousContentView = null
                return false
            }

            val previousActivityContainer = getContentView(mPreviousActivity!!.window)
            if (previousActivityContainer == null || previousActivityContainer.childCount == 0) {
                mPreviousActivity = null
                mPreviousContentView = null
                return false
            }

            mPreviousContentView = previousActivityContainer.getChildAt(0) as ViewGroup
            previousActivityContainer.removeView(mPreviousContentView)
            mCurrentContentView.addView(mPreviousContentView, 0)
            return true
        }

        /**
         * Remove the PreviousContentView at current Activity and put it into previous Activity.
         */
        private fun resetPreviousView() {
            if (mPreviousContentView == null) return

            val view = mPreviousContentView
            val contentView = mCurrentContentView
            view!!.x = 0f
            contentView.removeView(view)
            mPreviousContentView = null

            if (mPreviousActivity == null || mPreviousActivity!!.isFinishing) return
            val preActivity = mPreviousActivity
            val previewContentView = getContentView(preActivity!!.window)
            previewContentView!!.addView(view)
            mPreviousActivity = null
        }

        /**
         * add shadow view on the left of content view
         */
        private fun addShadowView() {
            if (mShadowView == null) {
                mShadowView = ShadowView(context)
                mShadowView!!.x = (-SHADOW_WIDTH).toFloat()
            }
            val layoutParams = FrameLayout.LayoutParams(
                    SHADOW_WIDTH, FrameLayout.LayoutParams.MATCH_PARENT)
            val contentView = mCurrentContentView
            contentView.addView(mShadowView, 1, layoutParams)
        }

        private fun removeShadowView() {
            if (mShadowView == null) return
            val contentView = getContentView(mCurrentWindow)
            contentView!!.removeView(mShadowView)
            mShadowView = null
        }

        private fun addCacheView() {
            val contentView = mCurrentContentView
            val previousView = mPreviousContentView
            val previousPageView = PreviousPageView(context)
            contentView.addView(previousPageView, 0)
            previousPageView.cacheView(previousView)
        }

        private val displayView: View
            get() {
                var index = 0
                if (mViewManager.mPreviousContentView != null) {
                    index = index + 1
                }

                if (mViewManager.mShadowView != null) {
                    index = index + 1
                }
                return mCurrentContentView.getChildAt(index)
            }
    }

    companion object {

        private val TAG = "SwipeWindowHelper"

        private val CURRENT_POINT_X = "currentPointX" //点击事件

        private val MSG_ACTION_DOWN = 1 //点击事件
        private val MSG_ACTION_MOVE = 2 //滑动事件
        private val MSG_ACTION_UP = 3  //点击结束
        private val MSG_SLIDE_CANCEL = 4 //开始滑动，不返回前一个页面
        private val MSG_SLIDE_CANCELED = 5  //结束滑动，不返回前一个页面
        private val MSG_SLIDE_PROCEED = 6 //开始滑动，返回前一个页面
        private val MSG_SLIDE_FINISHED = 7//结束滑动，返回前一个页面

        private val SHADOW_WIDTH = 50 //px 阴影宽度
        private val MARGIN_THRESHOLD = 40  //px 默认拦截手势区间 0~40
    }
}

internal class PreviousPageView(context: Context) : View(context) {
    private var mView: View? = null

    fun cacheView(view: View) {
        mView = view
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        if (mView != null) {
            mView!!.draw(canvas)
            mView = null
        }
    }
}

internal class ShadowView(context: Context) : View(context) {
    private var mDrawable: Drawable? = null

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mDrawable == null) {
            val colors = intArrayOf(0x00000000, 0x17000000, 0x43000000)//分别为开始颜色，中间夜色，结束颜色
            mDrawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)
        }
        mDrawable!!.setBounds(0, 0, measuredWidth, measuredHeight)
        mDrawable!!.draw(canvas)
    }
}