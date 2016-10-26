package com.apanda.base.Utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager

import com.apanda.base.R


object DialogUtils {

    fun showDialogPrompt(context: Context, content: String) {
        AlertDialog.Builder(context).setTitle("提示").setMessage(content).setPositiveButton("确定") { dialog, which -> dialog.cancel() }.create().show()
    }

    fun showDialogPrompt(context: Context, title: String, content: String) {
        AlertDialog.Builder(context).setTitle(title).setMessage(content).setPositiveButton("确定") { dialog, which -> dialog.cancel() }.create().show()
    }


    fun showDialogPrompt(context: Context, title: String?, content: String, positiveListener: DialogInterface.OnClickListener) {
        var title = title
        if (title == null || title.length == 0) {
            title = "提示"
        }
        AlertDialog.Builder(context).setTitle(title).setMessage(content).setPositiveButton("确定", positiveListener).create().show()
    }

    /**
     * 两个按钮

     * @param context
     * *
     * @param title
     * *
     * @param content
     * *
     * @param positionStr
     * *
     * @param positiveListener
     */
    fun showDialogPrompt(context: Context, title: String?, content: String, positionStr: String, positiveListener: DialogInterface.OnClickListener): AlertDialog {
        var title = title
        if (title == null || title.length == 0) {
            title = "提示"
        }


        val alertDialog = AlertDialog.Builder(context).setTitle(title).setMessage(content).setPositiveButton(positionStr, positiveListener).setNegativeButton("取消") { dialog, which -> dialog.cancel() }.create()
        //   alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.show()

        return alertDialog

    }

    /**
     * 两个按钮的额功能都可以定义的dialog

     * @param context
     * *
     * @param title
     * *
     * @param content
     * *
     * @param positionStr
     * *
     * @param nagativeStr
     * *
     * @param positiveListener
     * *
     * @param negasitiveListener
     * *
     * @return
     */
    fun showDialogPrompt(context: Context, title: String?,
                         content: String,
                         positionStr: String, nagativeStr: String,
                         positiveListener: DialogInterface.OnClickListener,
                         negasitiveListener: DialogInterface.OnClickListener): AlertDialog {
        var title = title
        if (title == null || title.length == 0) {
            title = "提示"
        }


        val alertDialog = AlertDialog.Builder(context).setTitle(title).setMessage(content).setPositiveButton(positionStr, positiveListener).setNegativeButton(nagativeStr, negasitiveListener).create()
        alertDialog.show()


        return alertDialog

    }


    /**
     * 设置底部出现的Diaog

     * @param id      布局文件
     * *
     * @param context 上下文
     * *
     * @return dialog
     */
    fun showAlert(id: Int, context: Context): Dialog {
        val dialog = Dialog(context, R.style.Dialog_FS_TAB)

        //////设置为true点击区域外消失
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        dialog.setContentView(id)

        //自定义dialog的属性
        val dialogWindow = dialog.window
        val lp = dialogWindow!!.attributes
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT // 宽度
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT // 高度
        //lp.alpha = 0.7f; // 透明度

        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
        // dialog.onWindowAttributesChanged(lp);
        dialogWindow.attributes = lp
        //设置动画
        dialogWindow.setWindowAnimations(R.style.PopupAnimation)

        //        dialog.show();
        return dialog
    }

    fun showAlert(view: View, context: Context): Dialog {
        val dialog = Dialog(context, R.style.Dialog_FS_TAB)
        //////设置为true点击区域外消失
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        dialog.setContentView(view)

        //自定义dialog的属性
        val dialogWindow = dialog.window
        val lp = dialogWindow!!.attributes
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT // 宽度
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT // 高度
        //lp.alpha = 0.7f; // 透明度

        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
        // dialog.onWindowAttributesChanged(lp);
        dialogWindow.attributes = lp
        //设置动画
        dialogWindow.setWindowAnimations(R.style.PopupAnimation)

        dialog.show()
        return dialog
    }

    /**
     * 设置中间出现的Diaog

     * @param id      布局文件
     * *
     * @param context 上下文
     * *
     * @return dialog
     */
    fun showAlertMid(id: Int, context: Context): Dialog {

        val dialog = Dialog(context)
        //设置为true点击区域外消失
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(id)

        //自定义dialog的属性

        //自定义dialog的属性
        val dialogWindow = dialog.window
        val lp = dialogWindow!!.attributes
        dialogWindow.setGravity(Gravity.CENTER)
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT // 宽度
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT // 高度
        //lp.alpha = 0.7f; // 透明度

        dialog.show()
        return dialog
    }


    /**
     * 设置中间出现的Diaog

     * @param view    视图
     * *
     * @param context 上下文
     * *
     * @return dialog
     */
    fun showAlertMid(view: View, context: Context): Dialog {

        val dialog = Dialog(context)
        //设置为true点击区域外消失
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view)

        //自定义dialog的属性
        val dialogWindow = dialog.window
        val lp = dialogWindow!!.attributes
        dialogWindow.setGravity(Gravity.CENTER)
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT // 宽度
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT // 高度
        //lp.alpha = 0.7f; // 透明度
        return dialog
    }

}