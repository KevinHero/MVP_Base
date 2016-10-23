package com.apanda.base.callback;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.Nullable;

import com.lzy.okgo.request.BaseRequest;

import java.lang.reflect.Type;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 *
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端  
 * 包名: 
 * 作者: created by 熊凯 on 2016/9/29 11:07
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: DialogCallback 
 * 
 */
public abstract class DialogCallback<T> extends JsonCallback<T> {

    private SweetAlertDialog dialog;

    private void initDialog(Activity activity) {
        dialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setTitleText("请求网络中...");
        dialog.setCancelable(false);
        dialog.show();

    }




    public DialogCallback(Activity activity, Class<T> clazz) {
        super(clazz);
        initDialog(activity);
    }

    public DialogCallback(Activity activity, Type type) {
        super(type);
        initDialog(activity);
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        //网络请求前显示对话框
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onAfter(@Nullable T t, @Nullable Exception e) {
        super.onAfter(t, e);
        //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
