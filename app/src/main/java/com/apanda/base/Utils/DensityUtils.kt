package com.apanda.base.Utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

import com.apanda.base.base.BaseApplication


object DensityUtils {

    fun dpToPx(dp: Int): Int {
        val scale = BaseApplication.instance.screenDensity
        return (dp * scale + 0.5f).toInt()
    }

    fun dpToPxFloat(dp: Float): Float {
        val scale = BaseApplication.instance.screenDensity
        return dp * scale + 0.5f
    }

    fun pxToDp(px: Int): Int {
        val scale = BaseApplication.instance.screenDensity
        return (px / scale).toInt()
    }

    fun dp2px(context: Context, dp: Float): Int {
        val r = context.resources
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.displayMetrics)
        return Math.round(px)
    }


}
