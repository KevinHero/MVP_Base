package com.apanda.base.callback

import android.app.Activity
import android.graphics.Color

import com.lzy.okgo.request.BaseRequest

import java.lang.reflect.Type

import cn.pedant.SweetAlert.SweetAlertDialog

/**

 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端
 * 包名:
 * 作者: created by 熊凯 on 2016/9/29 11:07
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: DialogCallback

 */
abstract class DialogCallback<T> : JsonCallback<T> {

    private var dialog: SweetAlertDialog? = null

    private fun initDialog(activity: Activity) {
        dialog = SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE)
        dialog!!.progressHelper.barColor = Color.parseColor("#A5DC86")
        dialog!!.titleText = "请求网络中..."
        dialog!!.setCancelable(false)
        dialog!!.show()

    }


    constructor(activity: Activity, clazz: Class<T>) : super(clazz) {
        initDialog(activity)
    }

    constructor(activity: Activity, type: Type) : super(type) {
        initDialog(activity)
    }

    override fun onBefore(request: BaseRequest<*>?) {
        super.onBefore(request)
        //网络请求前显示对话框
        if (dialog != null && !dialog!!.isShowing) {
            dialog!!.show()
        }
    }

    override fun onAfter(t: T?, e: Exception?) {
        super.onAfter(t, e)
        //网络请求结束后关闭对话框
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }
}
