package com.apanda.base.callback

import android.app.Activity
import android.graphics.Color

import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.request.BaseRequest

import cn.pedant.SweetAlert.SweetAlertDialog


/**
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端
 * 包名:
 * 作者: created by 熊凯 on 2016/9/29 10:51
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: StringDialogCallback
 */
abstract class StringDialogCallback(activity: Activity) : StringCallback() {


    private val dialog: SweetAlertDialog?

    init {
        //        dialog = new ProgressDialog(activity);
        //        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //        dialog.setCanceledOnTouchOutside(false);
        //        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //        dialog.setMessage("请求网络中...");

        dialog = SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE)
        dialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        dialog.titleText = "请求网络中..."
        dialog.setCancelable(false)
        dialog.show()


    }

    override fun onBefore(request: BaseRequest<*>?) {
        super.onBefore(request)
        //网络请求前显示对话框
        if (dialog != null && !dialog.isShowing) {
            dialog.show()
        }
    }

    override fun onAfter(s: String?, e: Exception?) {
        super.onAfter(s, e)
        //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing) {
            dialog.dismiss()
        }
    }
}
