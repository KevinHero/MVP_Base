package com.apanda.base.Widget;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端
 * 作者: created by 熊凯 on 2016/10/11 14:52
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: ToastMgr
 */

public class ToastMgr {
    private static Toast it;

    private ToastMgr() {
    }

    /**
     * 在程序初始化的时候调用, 只需调用一次
     */
    public static void init(Context _context) {
        View v = Toast.makeText(_context, "", Toast.LENGTH_SHORT).getView();
        init(_context, v);
    }

    /**
     * 在程序初始化的时候调用, 只需调用一次
     */
    public static void init(Context _context, View view) {
        it = new Toast(_context);
        it.setView(view);
    }

    /**
     * 设置Toast背景
     */
    public static void setBackgroundView(View view) {
        checkInit();
        it.setView(view);
    }

    public static void show(CharSequence text, int duration) {
        checkInit();
        it.setText(text);
        it.setDuration(duration);
        it.show();
    }

    public static void show(int resid, int duration) {
        checkInit();
        it.setText(resid);
        it.setDuration(duration);
        it.show();
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    private static void checkInit() {
        if (it == null) {
            throw new IllegalStateException("ToastMgr is not initialized, please call init once before you call this method");
        }
    }
}