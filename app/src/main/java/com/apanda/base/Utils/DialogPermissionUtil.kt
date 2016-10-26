package com.apanda.base.Utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build

/**
 * des:权限对话框管理
 * Created by xsf
 * on 2016.06.10:21
 */
object DialogPermissionUtil {

    fun PermissionDialog(activity: Activity, content: String) {
        val deleteDialog = AlertDialog.Builder(activity).setTitle("提示").setMessage(content).setPositiveButton("去设置"
        ) { dialog, which -> startSettingIntent(activity) }.create()
        deleteDialog.show()
    }

    /**
     * 启动app设置授权界面
     * @param context
     */
    fun startSettingIntent(context: Context) {
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            localIntent.data = Uri.fromParts("package", context.packageName, null)
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.action = Intent.ACTION_VIEW
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails")
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.packageName)
        }
        context.startActivity(localIntent)
    }
}
