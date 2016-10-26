package com.apanda.base.Utils

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.WindowManager.LayoutParams

import java.lang.reflect.Method

/**
 *
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/8/2
 * desc  : 屏幕相关工具类
 *
 */
class ScreenUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't fuck me...")
    }

    companion object {

        /**
         * 获取屏幕的宽度px

         * @param context 上下文
         * *
         * @return 屏幕宽px
         */
        fun getScreenWidth(context: Context): Int {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()// 创建了一张白纸
            windowManager.defaultDisplay.getMetrics(outMetrics)// 给白纸设置宽高
            return outMetrics.widthPixels
        }

        /**
         * 获取屏幕的高度px

         * @param context 上下文
         * *
         * @return 屏幕高px
         */
        fun getScreenHeight(context: Context): Int {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()// 创建了一张白纸
            windowManager.defaultDisplay.getMetrics(outMetrics)// 给白纸设置宽高
            return outMetrics.heightPixels
        }

        /**
         * 设置透明状态栏(api大于19方可使用)
         *
         * 可在Activity的onCreat()中调用
         *
         * 需在顶部控件布局中加入以下属性让内容出现在状态栏之下
         *
         * android:clipToPadding="true"
         *
         * android:fitsSystemWindows="true"

         * @param activity activity
         */
        fun setTransparentStatusBar(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //透明状态栏
                activity.window.addFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS)
                //透明导航栏
                activity.window.addFlags(LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            }
        }

        /**
         * 隐藏状态栏
         *
         * 也就是设置全屏，一定要在setContentView之前调用，否则报错
         *
         * 此方法Activity可以继承AppCompatActivity
         *
         * 启动的时候状态栏会显示一下再隐藏，比如QQ的欢迎界面
         *
         * 在配置文件中Activity加属性android:theme="@android:style/Theme.NoTitleBar.Fullscreen"
         *
         * 如加了以上配置Activity不能继承AppCompatActivity，会报错

         * @param activity activity
         */
        fun hideStatusBar(activity: Activity) {
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
            activity.window.setFlags(LayoutParams.FLAG_FULLSCREEN,
                    LayoutParams.FLAG_FULLSCREEN)
        }

        /**
         * 获取状态栏高度

         * @param context 上下文
         * *
         * @return 状态栏高度
         */
        fun getStatusBarHeight(context: Context): Int {
            var result = 0
            val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

        /**
         * 判断状态栏是否存在

         * @param activity activity
         * *
         * @return `true`: 存在`false`: 不存在
         */
        fun isStatusBarExists(activity: Activity): Boolean {
            val params = activity.window.attributes
            return params.flags and LayoutParams.FLAG_FULLSCREEN != LayoutParams.FLAG_FULLSCREEN
        }

        /**
         * 获取ActionBar高度

         * @param activity activity
         * *
         * @return ActionBar高度
         */
        fun getActionBarHeight(activity: Activity): Int {
            val tv = TypedValue()
            if (activity.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                return TypedValue.complexToDimensionPixelSize(tv.data, activity.resources.displayMetrics)
            }
            return 0
        }

        /**
         * 显示通知栏
         *
         * 需添加权限 `&lt;uses-permission android:name=&quot;android.permission.EXPAND_STATUS_BAR&quot;/&gt;`

         * @param context        上下文
         * *
         * @param isSettingPanel `true`: 打开设置`false`: 打开通知
         */
        fun showNotificationBar(context: Context, isSettingPanel: Boolean) {
            val methodName = if (Build.VERSION.SDK_INT <= 16)
                "expand"
            else
                if (isSettingPanel) "expandSettingsPanel" else "expandNotificationsPanel"
            invokePanels(context, methodName)
        }

        /**
         * 隐藏通知栏
         *
         * 需添加权限 `&lt;uses-permission android:name=&quot;android.permission.EXPAND_STATUS_BAR&quot;/&gt;`

         * @param context 上下文
         */
        fun hideNotificationBar(context: Context) {
            val methodName = if (Build.VERSION.SDK_INT <= 16) "collapse" else "collapsePanels"
            invokePanels(context, methodName)
        }

        /**
         * 反射唤醒通知栏

         * @param context    上下文
         * *
         * @param methodName 方法名
         */
        private fun invokePanels(context: Context, methodName: String) {
            try {
                val service = context.getSystemService("statusbar")
                val statusBarManager = Class.forName("android.app.StatusBarManager")
                val expand = statusBarManager.getMethod(methodName)
                expand.invoke(service)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        /**
         * 设置屏幕为横屏
         *
         * 还有一种就是在Activity中加属性android:screenOrientation="landscape"
         *
         * 不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次
         *
         * 设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次
         *
         * 设置Activity的android:configChanges="orientation|keyboardHidden|screenSize"（4.0以上必须带最后一个参数）时
         * 切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法

         * @param activity activity
         */
        fun setLandscape(activity: Activity) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        /**
         * 获取当前屏幕截图，包含状态栏

         * @param activity activity
         * *
         * @return Bitmap
         */
        fun captureWithStatusBar(activity: Activity): Bitmap {
            val view = activity.window.decorView
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            val bmp = view.drawingCache
            val width = getScreenWidth(activity)
            val height = getScreenHeight(activity)
            val bp = Bitmap.createBitmap(bmp, 0, 0, width, height)
            view.destroyDrawingCache()
            return bp
        }

        /**
         * 获取当前屏幕截图，不包含状态栏
         *
         * 需要用到上面获取状态栏高度getStatusBarHeight的方法

         * @param activity activity
         * *
         * @return Bitmap
         */
        fun captureWithoutStatusBar(activity: Activity): Bitmap {
            val view = activity.window.decorView
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            val bmp = view.drawingCache
            val statusBarHeight = getStatusBarHeight(activity)
            val width = getScreenWidth(activity)
            val height = getScreenHeight(activity)
            val bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight)
            view.destroyDrawingCache()
            return bp
        }

        /**
         * 判断是否锁屏

         * @param context 上下文
         * *
         * @return `true`: 是`false`: 否
         */
        fun isScreenLock(context: Context): Boolean {
            val km = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            return km.inKeyguardRestrictedInputMode()
        }
    }
}