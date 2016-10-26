package com.apanda.base.Widget.Bottomdialog

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentManager
import android.view.View

/**

 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端
 * 包名:
 * 作者: created by 熊凯 on 2016/10/13 13:09
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: BottomDialog

 */

class BottomDialog : BaseBottomDialog() {

    private var mFragmentManager: FragmentManager? = null

    private var mIsCancelOutside = true

    @LayoutRes
    override var layoutRes: Int = 0
        private set

    private var mTag = TAG

    private var mDimAmount = 0.2f

    private var mHeight: Int = 0

    private var mViewListener: ViewListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            layoutRes = savedInstanceState.getInt(KEY_LAYOUT_RES)
            mHeight = savedInstanceState.getInt(KEY_HEIGHT)
            mDimAmount = savedInstanceState.getFloat(KEY_DIM)
            mIsCancelOutside = savedInstanceState.getBoolean(KEY_CANCEL_OUTSIDE)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState!!.putInt(KEY_LAYOUT_RES, layoutRes)
        outState.putInt(KEY_HEIGHT, mHeight)
        outState.putFloat(KEY_DIM, mDimAmount)
        outState.putBoolean(KEY_CANCEL_OUTSIDE, mIsCancelOutside)

        super.onSaveInstanceState(outState)
    }

    override fun bindView(v: View) {
        if (mViewListener != null) {
            mViewListener!!.bindView(v)
        }
    }

    fun setFragmentManager(manager: FragmentManager): BottomDialog {
        mFragmentManager = manager
        return this
    }

    fun setViewListener(listener: ViewListener): BottomDialog {
        mViewListener = listener
        return this
    }

    fun setLayoutRes(@LayoutRes layoutRes: Int): BottomDialog {
        this.layoutRes = layoutRes
        return this
    }

    fun setCancelOutside(cancel: Boolean): BottomDialog {
        mIsCancelOutside = cancel
        return this
    }

    fun setTag(tag: String): BottomDialog {
        mTag = tag
        return this
    }

    fun setDimAmount(dim: Float): BottomDialog {
        mDimAmount = dim
        return this
    }

    fun setHeight(heightPx: Int): BottomDialog {
        mHeight = heightPx
        return this
    }

    interface ViewListener {
        fun bindView(v: View)
    }

    fun show(): BaseBottomDialog {
        show(mFragmentManager!!, tag)
        return this
    }

    companion object {

        private val TAG = "bottom_dialog"

        private val KEY_LAYOUT_RES = "bottom_layout_res"
        private val KEY_HEIGHT = "bottom_height"
        private val KEY_DIM = "bottom_dim"
        private val KEY_CANCEL_OUTSIDE = "bottom_cancel_outside"

        fun create(manager: FragmentManager): BottomDialog {
            val dialog = BottomDialog()
            dialog.fragmentManager = manager
            return dialog
        }
    }
}
