package com.apanda.base.Widget.Bottomdialog


import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager

import com.apanda.base.R

/**

 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端
 * 包名:
 * 作者: created by 熊凯 on 2016/10/13 13:09
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: BaseBottomDialog

 */
abstract class BaseBottomDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(cancelOutside)

        val v = inflater!!.inflate(layoutRes, container, false)
        bindView(v)
        return v
    }

    abstract val layoutRes: Int

    abstract fun bindView(v: View)

    override fun onStart() {
        super.onStart()

        val window = dialog.window
        val params = window!!.attributes

        params.dimAmount = dimAmount
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        if (height > 0) {
            params.height = height
        } else {
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        params.gravity = Gravity.BOTTOM

        window.attributes = params
    }

    val height: Int
        get() = -1

    val dimAmount: Float
        get() = DEFAULT_DIM

    val cancelOutside: Boolean
        get() = true

    val fragmentTag: String
        get() = TAG

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, TAG)
    }

    companion object {

        private val TAG = "base_bottom_dialog"

        private val DEFAULT_DIM = 0.2f
    }
}
