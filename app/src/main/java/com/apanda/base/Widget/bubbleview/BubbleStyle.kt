package com.apanda.base.Widget.bubbleview

import android.view.View

import java.util.HashMap

/**
 * 气泡View抽象接口

 * Created by caijw on 2016/6/1.
 * https://github.com/cpiz/BubbleView
 */
interface BubbleStyle {
    /**
     * 箭头朝向枚举
     */
    enum class ArrowDirection private constructor(value: Int) {
        None(0),
        Left(1),
        Up(2),
        Right(3),
        Down(4);

        private var mValue = 0

        init {
            mValue = value
        }

        val isLeft: Boolean
            get() = this == Left

        val isUp: Boolean
            get() = this == Up

        val isRight: Boolean
            get() = this == Right

        val isDown: Boolean
            get() = this == Down

        companion object {

            private val intToTypeMap = HashMap<Int, ArrowDirection>()

            init {
                for (type in ArrowDirection.values()) {
                    intToTypeMap.put(type.mValue, type)
                }
            }

            fun valueOf(value: Int): ArrowDirection {
                val type = intToTypeMap[value] ?: return ArrowDirection.None
                return type
            }
        }
    }

    /**
     * 设置箭头朝向

     * @param arrowDirection 上下左右或者无
     */
    var arrowDirection: ArrowDirection

    /**
     * 设置箭头三角形厚度

     * @param arrowHeight 箭头厚度
     */
    var arrowHeight: Float

    /**
     * 设置箭头三角形底宽

     * @param arrowWidth 箭头底边宽度
     */
    var arrowWidth: Float

    /**
     * 设置箭头在边线上的位置，视箭头方向而定

     * @param arrowOffset 根据箭头位置，偏移像素值：
     * *                    朝上/下时在X轴方向偏移，>0 时从正方向偏移，<0时从负方向偏移
     * *                    朝左/右时在Y轴方向偏移，>0 时从正方向偏移，<0时从负方向偏移
     */
    var arrowOffset: Float

    /**
     * 设置箭头指向的View对象
     * 设置了View对象后，setArrowPos将不起作用

     * @param viewId 指向的ViewId
     */
    fun setArrowTo(viewId: Int)

    fun setArrowTo(view: View)

    val arrowTo: View

    /**
     * 设置气泡背景色

     * @param fillColor 气泡背景颜色
     */
    var fillColor: Int

    /**
     * 设置边框线颜色

     * @param borderColor 边框颜色
     */
    var borderColor: Int

    /**
     * 设置边框线宽

     * @param borderWidth 边框厚度
     */
    var borderWidth: Float

    /**
     * 设置边框于背景之间的间隙宽度

     * @param fillPadding 间隙宽度
     */
    var fillPadding: Float

    /**
     * 设置边角弧度
     * 可以为四角指定不同弧度

     * @param topLeft     左上角
     * *
     * @param topRight    右上角
     * *
     * @param bottomRight 右下角
     * *
     * @param bottomLeft  左下角
     */
    fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float)

    fun setCornerRadius(radius: Float)

    val cornerTopLeftRadius: Float

    val cornerTopRightRadius: Float

    val cornerBottomLeftRadius: Float

    val cornerBottomRightRadius: Float

    /**
     * 设定Padding
     * 将自动将箭头区域占用空间加入Padding，使内容能够完全被气泡包含

     * @param left   用户指定的 LeftPadding
     * *
     * @param top    用户指定的 TopPadding
     * *
     * @param right  用户指定的 RightPadding
     * *
     * @param bottom 用户指定的 BottomPadding
     */
    fun setPadding(left: Int, top: Int, right: Int, bottom: Int)

    val paddingLeft: Int

    val paddingTop: Int

    val paddingRight: Int

    val paddingBottom: Int

    fun updateDrawable()
}
