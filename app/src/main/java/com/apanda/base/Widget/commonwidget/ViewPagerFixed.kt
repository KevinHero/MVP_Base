package com.apanda.base.Widget.commonwidget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * des:修复viewpager
 * Created by xsf
 * on 2016.07.15:33
 */
class ViewPagerFixed : android.support.v4.view.ViewPager {

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        try {
            return super.onTouchEvent(ev)
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        }

        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        try {
            return super.onInterceptTouchEvent(ev)
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        }

        return false
    }
}
