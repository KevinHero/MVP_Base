package com.apanda.base.Widget

import android.content.Context
import android.view.View
import android.widget.Toast

/**
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端
 * 作者: created by 熊凯 on 2016/10/11 14:52
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: ToastMgr
 */

object ToastMgr {
    private var it: Toast? = null

    /**
     * 在程序初始化的时候调用, 只需调用一次
     */
    fun init(_context: Context) {
        val v = Toast.makeText(_context, "", Toast.LENGTH_SHORT).view
        init(_context, v)
    }

    /**
     * 在程序初始化的时候调用, 只需调用一次
     */
    fun init(_context: Context, view: View) {
        it = Toast(_context)
        it!!.view = view
    }

    /**
     * 设置Toast背景
     */
    fun setBackgroundView(view: View) {
        checkInit()
        it!!.view = view
    }

    @JvmOverloads fun show(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        checkInit()
        it!!.setText(text)
        it!!.duration = duration
        it!!.show()
    }

    fun show(resid: Int, duration: Int) {
        checkInit()
        it!!.setText(resid)
        it!!.duration = duration
        it!!.show()
    }

    fun show(resId: Int) {
        show(resId, Toast.LENGTH_SHORT)
    }

    private fun checkInit() {
        if (it == null) {
            throw IllegalStateException("ToastMgr is not initialized, please call init once before you call this method")
        }
    }
}