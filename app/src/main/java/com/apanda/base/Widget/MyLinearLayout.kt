package com.apanda.base.Widget

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView

import com.apanda.base.Utils.logger.L


/**
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端
 * 包名:
 * 作者: created by 熊凯 on 2016/10/8 12:59
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: MyLinearLayout
 */


class MyLinearLayout : ViewGroup {
    private var context: Context? = null

    private var childHeight: Int = 0
    private var childWidthRatio: Int = 0

    private var chidlWidthSpace: Int = 0
    private var childHeightSpace: Int = 0

    constructor(context: Context) : super(context) {
        this.context = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.context = context
    }

    /**
     * 统一设置childView的高度;通过该值可以计算整体布局的高度

     * @param childHeight
     */
    fun setChildHeight(childHeight: Int) {
        this.childHeight = childHeight
    }

    /**
     * 统一设置childView的宽度比例系数。textView.length * ratio

     * @param childWidthRatio
     */
    fun setChildWidthRatio(childWidthRatio: Int) {
        this.childWidthRatio = childWidthRatio
    }

    /**
     * 设置childView之间的左右间隔大小

     * @param childViewWidthSpace
     */
    fun setChildViewWidthSpace(childViewWidthSpace: Int) {
        chidlWidthSpace = childViewWidthSpace
        requestLayout()
    }

    /**
     * 设置childView之间的上下间隔大小

     * @param childHeightSpace
     */
    fun setChildHeightSpace(childHeightSpace: Int) {
        this.childHeightSpace = childHeightSpace
        requestLayout()
    }

    /**
     * 计算控件及子控件所占区域
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 父控件宽度
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)

        // 父控件高度--方法：累计每个子控件的宽度+左右间隔，当数值超过父控件的宽度时，换行。记录行数。
        // 记录ViewGroup中Child的总个数
        val count = childCount
        var lines = 1

        var currWidth = 0 // 每行当前宽度总和
        for (index in 0..count - 1) {

            val child = getChildAt(index) as TextView
            val childWidth = getWidth(child)

            currWidth += chidlWidthSpace + childWidth//左侧间隔+子控件宽度

            // 另起一行
            if (currWidth > widthSize) {
                currWidth = 0
                currWidth += chidlWidthSpace + childWidth//左侧间隔+子控件宽度
                lines++
            }
        }
        //  子控件高度+间隔
        val heightSize = childHeight * lines + (lines + 1) * childHeightSpace

        //        L.d("widthSize = " + widthSize + " heightSize = " + heightSize);
        setMeasuredDimension(widthSize, heightSize)
    }

    /**
     * 子控件的宽度通过计算字符数转化而来

     * @param view
     * *
     * @return
     */
    private fun getWidth(view: TextView): Int {
        //getPxFromDp
        val width = getPxFromDp(context, (view.length() * childWidthRatio).toDouble()).toInt()
        return width
    }

    private fun getWidth(view: ImageView): Int {
        //getPxFromDp
        val width = getPxFromDp(context, (view.horizontalFadingEdgeLength * childWidthRatio).toDouble()).toInt()
        return width
    }

    /**
     * 控制子控件的换行
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        L.d("changed = $changed left = $l top = $t right = $r bottom = $b")

        val parentWidth = r - l
        // int parentHeight = b - t;

        var x = 0
        var y = 0

        val count = childCount
        for (j in 0..count - 1) {
            val childView = getChildAt(j) as TextView
            // 获取子控件Child的宽高
            val w = getWidth(childView)
            val h = childHeight
            // 计算子控件的顶点坐标
            var left = x + chidlWidthSpace
            var top = y + childHeightSpace
            var right = left + w
            var bottom = top + h


            if (right > parentWidth) { // 子控件右侧坐标超过父控件，则换行
                x = 0
                y = bottom

                left = x + chidlWidthSpace
                top = y + childHeightSpace
                right = left + w
                bottom = top + h
            }

            //  x向右平移
            x = right

            // 布局子控件
            childView.layout(left, top, right, bottom)
            childView.visibility = View.VISIBLE
            println(childView.measuredWidth + ":" + childView.measuredHeight)
        }

    }

    companion object {

        private var metrics: DisplayMetrics? = null

        fun getPxFromDp(context: Context, dp: Double): Double {
            getDisplayMetrics(context)
            return dp * (metrics!!.densityDpi / 160)
        }

        private fun getDisplayMetrics(context: Context): DisplayMetrics {
            if (metrics == null) {
                metrics = DisplayMetrics()
                val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                manager.defaultDisplay.getMetrics(metrics)
            }

            return metrics
        }
    }
}


//extends ViewGroup {
//    private final int cellHeight = 70;
//
//    /**
//     * 知识点： 1、该自定义控件必须实现这个构造方法，不然会报android.view.InflateException: Binary XML
//     * file line #异常 2、另外两种构造方法 MyLinearLayout(Context
//     * context)、MyLinearLayout(Context context, AttributeSet attrs, int
//     * defStyle)可不实现!
//     */
//    public MyLinearLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    /**
//     * 控制子控件的换行
//     */
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        // TODO Auto-generated method stub
//        int left = 40;// 子控件的左上角x坐标
//        int top = 20;// 子控件的左上角y坐标
//        int count = getChildCount();// 获得子控件的数目
//        int remainingWidth = 0;// 计算一行中剩下的宽度
//
//        for (int j = 0; j < count; j++) {
//            View childView = getChildAt(j);
//            // 获取子控件Child的宽高
//            int w = childView.getMeasuredWidth();
//            int h = childView.getMeasuredHeight();
//
//            if (left != 40 && remainingWidth < w)// 如果即将显示的子控件不是位于第一列且该行位置已容不下该控件，则修改坐标参数换行显示！
//            {
//                left = 40;
//                top += (cellHeight + 20);
//            }
//            childView.layout(left, top, left + w, top + h);
//            remainingWidth = r - l - left - w - 80;
//            left += (w + 40);
//        }
//    }
//
//    /**
//     * 计算控件及子控件所占区域
//     */
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        // 创建测量参数
//        int cellWidthSpec = MeasureSpec.makeMeasureSpec(0,
//                MeasureSpec.UNSPECIFIED);// 不指定子控件的宽度
//        int cellHeightSpec = MeasureSpec.makeMeasureSpec(cellHeight,
//                MeasureSpec.EXACTLY);// 精确定义子控件的高度
//        int count = getChildCount();// 记录ViewGroup中Child的总个数
//
//        // 设置子控件Child的宽高
//        for (int i = 0; i < count; i++) {
//            View childView = getChildAt(i);
//            childView.measure(cellWidthSpec, cellHeightSpec);
//        }
//
//        // 设置容器控件所占区域大小
//        setMeasuredDimension(resolveSize(0, widthMeasureSpec),
//                resolveSize(cellHeight * count, heightMeasureSpec));
//    }
//}