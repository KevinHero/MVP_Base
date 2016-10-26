package com.apanda.base.callback

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color

import com.lzy.okgo.callback.BitmapCallback
import com.lzy.okgo.request.BaseRequest

import cn.pedant.SweetAlert.SweetAlertDialog


/**
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端
 * 包名:
 * 作者: created by 熊凯 on 2016/9/29 11:07
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: BitmapDialogCallback
 */
abstract class BitmapDialogCallback(activity: Activity) : BitmapCallback() {

    private val dialog: SweetAlertDialog?

    init {
        dialog = SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE)
        dialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        dialog.titleText = "请求网络中..."
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun onBefore(request: BaseRequest<*>?) {
        if (dialog != null && !dialog.isShowing) {
            dialog.show()
        }
    }

    override fun onAfter(bitmap: Bitmap?, e: Exception?) {
        if (dialog != null && dialog.isShowing) {
            dialog.dismiss()
        }
    }
}
