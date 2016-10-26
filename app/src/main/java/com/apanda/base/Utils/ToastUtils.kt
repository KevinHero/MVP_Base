package com.apanda.base.Utils

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.apanda.base.R
import com.apanda.base.base.BaseApplication


/**
 * Toast统一管理类
 */
object ToastUtils {


    private var toast: Toast? = null
    private var toast2: Toast? = null

    private fun initToast(message: CharSequence, duration: Int): Toast {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.instance, message, duration)
        } else {
            toast!!.setText(message)
            toast!!.duration = duration
        }
        return toast
    }

    /**
     * 短时间显示Toast

     * @param message
     */
    fun showShort(message: CharSequence) {
        initToast(message, Toast.LENGTH_SHORT).show()
    }


    /**
     * 短时间显示Toast

     * @param strResId
     */
    fun showShort(strResId: Int) {
        //		Toast.makeText(context, strResId, Toast.LENGTH_SHORT).show();
        initToast(BaseApplication.instance.getResources().getText(strResId), Toast.LENGTH_SHORT).show()
    }

    /**
     * 长时间显示Toast

     * @param message
     */
    fun showLong(message: CharSequence) {
        initToast(message, Toast.LENGTH_LONG).show()
    }

    /**
     * 长时间显示Toast

     * @param strResId
     */
    fun showLong(strResId: Int) {
        initToast(BaseApplication.instance.getResources().getText(strResId), Toast.LENGTH_LONG).show()
    }

    /**
     * 自定义显示Toast时间

     * @param message
     * *
     * @param duration
     */
    fun show(message: CharSequence, duration: Int) {
        initToast(message, duration).show()
    }

    /**
     * 自定义显示Toast时间

     * @param context
     * *
     * @param strResId
     * *
     * @param duration
     */
    fun show(context: Context, strResId: Int, duration: Int) {
        initToast(context.resources.getText(strResId), duration).show()
    }

    /**
     * 显示有image的toast

     * @param tvStr
     * *
     * @param imageResource
     * *
     * @return
     */
    fun showToastWithImg(tvStr: String, imageResource: Int): Toast {
        if (toast2 == null) {
            toast2 = Toast(BaseApplication.instance)
        }
        val view = LayoutInflater.from(BaseApplication.instance).inflate(R.layout.toast_custom, null)
        val tv = view.findViewById(R.id.toast_custom_tv) as TextView
        tv.text = if (TextUtils.isEmpty(tvStr)) "" else tvStr
        val iv = view.findViewById(R.id.toast_custom_iv) as ImageView
        if (imageResource > 0) {
            iv.visibility = View.VISIBLE
            iv.setImageResource(imageResource)
        } else {
            iv.visibility = View.GONE
        }
        toast2!!.view = view
        toast2!!.setGravity(Gravity.CENTER, 0, 0)
        toast2!!.show()
        return toast2

    }
}
