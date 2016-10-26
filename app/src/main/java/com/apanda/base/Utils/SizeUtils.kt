package com.apanda.base.Utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View

/**
 *
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/8/2
 * desc  : 尺寸相关工具类
 *
 */
class SizeUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't fuck me...")
    }

    /**
     * 获取到View尺寸的监听
     */
    interface onGetSizeListener {
        fun onGetSize(view: View)
    }

    companion object {

        /**
         * dp转px

         * @param context 上下文
         * *
         * @param dpValue dp值
         * *
         * @return px值
         */
        fun dp2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * px转dp

         * @param context 上下文
         * *
         * @param pxValue px值
         * *
         * @return dp值
         */
        fun px2dp(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * sp转px

         * @param context 上下文
         * *
         * @param spValue sp值
         * *
         * @return px值
         */
        fun sp2px(context: Context, spValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        /**
         * px转sp

         * @param context 上下文
         * *
         * @param pxValue px值
         * *
         * @return sp值
         */
        fun px2sp(context: Context, pxValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        /**
         * 各种单位转换
         *
         * 该方法存在于TypedValue

         * @param unit    单位
         * *
         * @param value   值
         * *
         * @param metrics DisplayMetrics
         * *
         * @return 转换结果
         */
        fun applyDimension(unit: Int, value: Float, metrics: DisplayMetrics): Float {
            when (unit) {
                TypedValue.COMPLEX_UNIT_PX -> return value
                TypedValue.COMPLEX_UNIT_DIP -> return value * metrics.density
                TypedValue.COMPLEX_UNIT_SP -> return value * metrics.scaledDensity
                TypedValue.COMPLEX_UNIT_PT -> return value * metrics.xdpi * (1.0f / 72)
                TypedValue.COMPLEX_UNIT_IN -> return value * metrics.xdpi
                TypedValue.COMPLEX_UNIT_MM -> return value * metrics.xdpi * (1.0f / 25.4f)
            }
            return 0f
        }

        /**
         * 在onCreate()即可强行获取View的尺寸
         *
         * 需回调onGetSizeListener接口，在onGetSize中获取view宽高
         *
         * 用法示例如下所示
         *
         * SizeUtils.forceGetViewSize(view);
         * SizeUtils.setListener(new SizeUtils.onGetSizeListener() {
         * `@Override`
         * public void onGetSize(View view) {
         * Log.d("tag", view.getWidth() + " " + view.getHeight());
         * }
         * });
         *

         * @param view 视图
         */
        fun forceGetViewSize(view: View) {
            view.post {
                if (mListener != null) {
                    mListener!!.onGetSize(view)
                }
            }
        }

        fun setListener(listener: onGetSizeListener) {
            mListener = listener
        }

        private var mListener: onGetSizeListener? = null

        /**
         * ListView中提前测量View尺寸，如headerView
         *
         * 用的时候去掉注释拷贝到ListView中即可
         *
         * 参照以下注释代码

         * @param view 视图
         */
        fun measureViewInLV(view: View) {
            Log.i("tips", "U should copy the following code.")
            /*
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeight = p.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight,
                    MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
        */
        }
    }
}
