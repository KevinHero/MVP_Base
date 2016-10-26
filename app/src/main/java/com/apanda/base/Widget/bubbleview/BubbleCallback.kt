package com.apanda.base.Widget.bubbleview

/**
 * 给BubbleImpl使用的回调接口，方便在BubbleImpl类中调用真正View的父类接口

 * Created by caijw on 2016/6/1.
 * https://github.com/cpiz/BubbleView
 */
internal interface BubbleCallback {
    fun setSuperPadding(left: Int, top: Int, right: Int, bottom: Int)

    val superPaddingLeft: Int

    val superPaddingTop: Int

    val superPaddingRight: Int

    val superPaddingBottom: Int
}
