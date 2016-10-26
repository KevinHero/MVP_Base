package com.apanda.base.Utils

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator

class AnimHelper private constructor() {

    init {
        throw RuntimeException("AnimHelper cannot be initialized!")
    }

    companion object {

        val INTERPOLATOR: Interpolator = FastOutSlowInInterpolator()
        val DURATION = 300

        fun scaleShow(view: View, listener: ViewPropertyAnimatorListener) {
            ViewCompat.animate(view).scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(DURATION.toLong()).setListener(listener).setInterpolator(INTERPOLATOR).withLayer().start()
        }

        fun scaleHide(view: View, listener: ViewPropertyAnimatorListener) {
            ViewCompat.animate(view).scaleX(0f).scaleY(0f).alpha(0f).setDuration(DURATION.toLong()).setListener(listener).setInterpolator(INTERPOLATOR).withLayer().start()
        }

        fun alphaShow(view: View, listener: ViewPropertyAnimatorListener) {
            ViewCompat.animate(view).alpha(1.0f).setDuration(DURATION.toLong()).setListener(listener).setInterpolator(INTERPOLATOR).withLayer().start()
        }

        fun alphaHide(view: View, listener: ViewPropertyAnimatorListener) {
            ViewCompat.animate(view).alpha(0f).setDuration(DURATION.toLong()).setListener(listener).setInterpolator(INTERPOLATOR).withLayer().start()
        }

        fun translateUp(view: View, listener: ViewPropertyAnimatorListener) {
            ViewCompat.animate(view).translationY(0f).setDuration(DURATION.toLong()).setListener(listener).setInterpolator(INTERPOLATOR).withLayer().start()
        }

        fun translateDown(view: View, listener: ViewPropertyAnimatorListener) {
            var height = view.height
            val params = view.layoutParams
            val layoutParams = if (params is ViewGroup.MarginLayoutParams) params else null
            if (layoutParams != null) height += layoutParams.bottomMargin
            ViewCompat.animate(view).translationY(height.toFloat()).setDuration(DURATION.toLong()).setListener(listener).setInterpolator(INTERPOLATOR).withLayer().start()
        }

        fun floatEvaluator(originalSize: Float, finalSize: Float, percent: Float): Float {
            return (finalSize - originalSize) * percent + originalSize
        }

        fun argbEvaluator(startColor: Int, endColor: Int, percent: Float): Int {
            val startA = startColor shr 24 and 0xff
            val startR = startColor shr 16 and 0xff
            val startG = startColor shr 8 and 0xff
            val startB = startColor and 0xff

            val endA = endColor shr 24 and 0xff
            val endR = endColor shr 16 and 0xff
            val endG = endColor shr 8 and 0xff
            val endB = endColor and 0xff

            return startA + (percent * (endA - startA)).toInt() shl 24 or
                    (startR + (percent * (endR - startR)).toInt() shl 16) or
                    (startG + (percent * (endG - startG)).toInt() shl 8) or
                    startB + (percent * (endB - startB)).toInt()
        }

        fun scaleEvaluator(originalSize: Float, finalSize: Float, percent: Float): Float {
            val calcSize = (finalSize - originalSize) * percent + originalSize
            return calcSize / originalSize
        }

        /** 获得状态栏的高度  */
        fun getStatusBarHeight(context: Context): Int {
            var statusHeight = -1
            try {
                val clazz = Class.forName("com.android.internal.R\$dimen")
                val `object` = clazz.newInstance()
                val height = Integer.parseInt(clazz.getField("status_bar_height").get(`object`).toString())
                statusHeight = context.resources.getDimensionPixelSize(height)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return statusHeight
        }
    }
}