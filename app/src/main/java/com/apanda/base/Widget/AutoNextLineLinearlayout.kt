package com.apanda.base.Widget


import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout

import java.util.Hashtable

/**
 * 自动换行的LinearLayout
 */

/**
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端
 * 作者: created by 熊凯 on 2016/10/9 16:50
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: AutoNextLineLinearlayout
 */
@SuppressWarnings("unchecked", "rawtypes")
class AutoNextLineLinearlayout : LinearLayout {
    internal var mLeft: Int = 0
    internal var mRight: Int = 0
    internal var mTop: Int = 0
    internal var mBottom: Int = 0
    internal var map = Hashtable()

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, horizontalSpacing: Int, verticalSpacing: Int) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val mWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val mCount = childCount
        var mX = 0
        var mY = 0
        mLeft = 0
        mRight = 0
        mTop = 5
        mBottom = 0

        var j = 0

        val lastview: View? = null
        for (i in 0..mCount - 1) {
            val child = getChildAt(i)

            child.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            // 此处增加onlayout中的换行判断，用于计算所需的高度
            val childw = child.measuredWidth
            val childh = child.measuredHeight
            mX += childw // 将每次子控件宽度进行统计叠加，如果大于设定的高度则需要换行，高度即Top坐标也需重新设置

            val position = Position()
            mLeft = getPosition(i - j, i)
            mRight = mLeft + child.measuredWidth
            if (mX >= mWidth) {
                mX = childw
                mY += childh
                j = i
                mLeft = 0
                mRight = mLeft + child.measuredWidth + 10
                mTop = mY + 8
                // PS：如果发现高度还是有问题就得自己再细调了
            }
            mBottom = mTop + child.measuredHeight + 8
            mY = mTop // 每次的高度必须记录 否则控件会叠加到一起
            position.left = mLeft + 10
            position.top = mTop + 3
            position.right = mRight + 10
            position.bottom = mBottom + 3
            map.put(child, position)
        }
        setMeasuredDimension(mWidth, mBottom)
    }

    override fun generateDefaultLayoutParams(): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(0, 0) // default of 1px spacing
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        val count = childCount
        for (i in 0..count - 1) {
            val child = getChildAt(i)
            if (map.get(child) != null) {
                child.layout(map.get(child).left, map.get(child).top, map.get(child).right, map.get(child).bottom)
            } else {
                Log.i("MyLayout", "error")
            }
        }
    }

    private inner class Position {
        internal var left: Int = 0
        internal var top: Int = 0
        internal var right: Int = 0
        internal var bottom: Int = 0
    }

    fun getPosition(IndexInRow: Int, childIndex: Int): Int {
        if (IndexInRow > 0) {
            return getPosition(IndexInRow - 1, childIndex - 1) + getChildAt(childIndex - 1).measuredWidth + 8
        }
        return paddingLeft
    }
}