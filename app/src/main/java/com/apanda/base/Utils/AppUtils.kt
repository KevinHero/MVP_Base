package com.apanda.base.Utils

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri

import java.io.File
import java.util.ArrayList

/**
 *
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/8/2
 * desc  : App相关工具类
 *
 */
class AppUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't fuck me...")
    }

    /**
     * 卸载指定包名的App

     * @param context     上下文
     * *
     * @param packageName 包名
     */
    fun uninstallApp(context: Context, packageName: String) {
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = Uri.parse("package:" + packageName)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    /**
     * 封装App信息的Bean类
     */
    class AppInfo
    /**
     * @param name        名称
     * *
     * @param icon        图标
     * *
     * @param packageName 包名
     * *
     * @param packagePath 包路径
     * *
     * @param versionName 版本号
     * *
     * @param versionCode 版本Code
     * *
     * @param isSD        是否安装在SD卡
     * *
     * @param isUser      是否是用户程序
     */
    (name: String, icon: Drawable, packageName: String, packagePath: String,
     versionName: String, versionCode: Int, isSD: Boolean, isUser: Boolean) {

        var name: String? = null
        var icon: Drawable? = null
        var packageName: String? = null
        var packagePath: String? = null
        var versionName: String? = null
        var versionCode: Int = 0
        var isSD: Boolean = false
        var isUser: Boolean = false

        init {
            this.name = name
            this.icon = icon
            this.packageName = packageName
            this.packagePath = packagePath
            this.versionName = versionName
            this.versionCode = versionCode
            this.isSD = isSD
            this.isUser = isUser
        }

        //        @Override
        //        public String toString() {
        //            return getName() + "\n"
        //                    + getIcon() + "\n"
        //                    + getPackageName() + "\n"
        //                    + getPackagePath() + "\n"
        //                    + getVersionName() + "\n"
        //                    + getVersionCode() + "\n"
        //                    + isSD() + "\n"
        //                    + isUser() + "\n";
        //        }
    }

    companion object {

        /**
         * 安装App
         *
         * 根据路径安装App

         * @param context  上下文
         * *
         * @param filePath 文件路径
         */
        fun installApp(context: Context, filePath: String) {
            installApp(context, File(filePath))
        }

        /**
         * 安装App
         *
         * 根据文件安装App

         * @param context 上下文
         * *
         * @param file    文件
         */
        fun installApp(context: Context, file: File) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        /**
         * 获取当前App信息
         *
         * AppInfo（名称，图标，包名，版本号，版本Code，是否安装在SD卡，是否是用户程序）

         * @param context 上下文
         * *
         * @return 当前应用的AppInfo
         */
        fun getAppInfo(context: Context): AppInfo? {
            val pm = context.packageManager
            var pi: PackageInfo? = null
            try {
                pi = pm.getPackageInfo(context.applicationContext.packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return if (pi != null) getBean(pm, pi) else null
        }

        /**
         * 得到AppInfo的Bean

         * @param pm 包的管理
         * *
         * @param pi 包的信息
         * *
         * @return AppInfo类
         */
        private fun getBean(pm: PackageManager, pi: PackageInfo): AppInfo {
            val ai = pi.applicationInfo
            val name = ai.loadLabel(pm).toString()
            val icon = ai.loadIcon(pm)
            val packageName = pi.packageName
            val packagePath = ai.sourceDir
            val versionName = pi.versionName
            val versionCode = pi.versionCode
            val isSD = ApplicationInfo.FLAG_SYSTEM and ai.flags != ApplicationInfo.FLAG_SYSTEM
            val isUser = ApplicationInfo.FLAG_SYSTEM and ai.flags != ApplicationInfo.FLAG_SYSTEM
            return AppInfo(name, icon, packageName, packagePath, versionName, versionCode, isSD, isUser)
        }

        /**
         * 获取所有已安装App信息
         *
         * [.getBean]（名称，图标，包名，包路径，版本号，版本Code，是否安装在SD卡，是否是用户程序）
         *
         * 依赖上面的getBean方法

         * @param context 上下文
         * *
         * @return 所有已安装的AppInfo列表
         */
        fun getAllAppsInfo(context: Context): List<AppInfo> {
            val list = ArrayList<AppInfo>()
            val pm = context.packageManager
            // 获取系统中安装的所有软件信息
            val installedPackages = pm.getInstalledPackages(0)
            for (pi in installedPackages) {
                if (pi != null) {
                    list.add(getBean(pm, pi))
                }
            }
            return list
        }

        /**
         * 根据包名获取意图

         * @param context     上下文
         * *
         * @param packageName 包名
         * *
         * @return 意图
         */
        private fun getIntentByPackageName(context: Context, packageName: String): Intent? {
            return context.packageManager.getLaunchIntentForPackage(packageName)
        }

        /**
         * 根据包名判断App是否安装

         * @param context     上下文
         * *
         * @param packageName 包名
         * *
         * @return `true`: 已安装`false`: 未安装
         */
        fun isInstallApp(context: Context, packageName: String): Boolean {
            return getIntentByPackageName(context, packageName) != null
        }

        /**
         * 打开指定包名的App

         * @param context     上下文
         * *
         * @param packageName 包名
         * *
         * @return `true`: 打开成功`false`: 打开失败
         */
        fun openAppByPackageName(context: Context, packageName: String): Boolean {
            val intent = getIntentByPackageName(context, packageName)
            if (intent != null) {
                context.startActivity(intent)
                return true
            }
            return false
        }

        /**
         * 打开指定包名的App应用信息界面

         * @param context     上下文
         * *
         * @param packageName 包名
         */
        fun openAppInfo(context: Context, packageName: String) {
            val intent = Intent()
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            intent.data = Uri.parse("package:" + packageName)
            context.startActivity(intent)
        }

        /**
         * 可用来做App信息分享

         * @param context 上下文
         * *
         * @param info    分享信息
         */
        fun shareAppInfo(context: Context, info: String) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, info)
            context.startActivity(intent)
        }

        /**
         * 判断当前App处于前台还是后台
         *
         * 需添加权限 `&lt;uses-permission android:name=&quot;android.permission.GET_TASKS&quot;/&gt;`
         *
         * 并且必须是系统应用该方法才有效

         * @param context 上下文
         * *
         * @return `true`: 后台`false`: 前台
         */
        fun isAppBackground(context: Context): Boolean {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            @SuppressWarnings("deprecation")
            val tasks = am.getRunningTasks(1)
            if (!tasks.isEmpty()) {
                val topActivity = tasks[0].topActivity
                if (topActivity.packageName != context.packageName) {
                    return true
                }
            }
            return false
        }
    }
}