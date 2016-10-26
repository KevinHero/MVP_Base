package com.apanda.base.Widget.commonwidget

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView

/**
 * 不会滚动的listview
 */

class NoScrollListview : ListView {
    constructor(context: Context) : super(context) {
        // TODO Auto-generated constructor stub
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2,
                View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }
}