package com.apanda.base.Utils

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri

import java.io.File
import java.io.IOException
import java.util.ArrayList
import java.util.Enumeration
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/1/25
 * 描    述：
 * 修订历史：
 * ================================================
 */
object ApkUtils {

    /** 安装一个apk文件  */
    fun install(context: Context, uriFile: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.fromFile(uriFile), "application/vnd.android.package-archive")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    /** 卸载一个app  */
    fun uninstall(context: Context, packageName: String) {
        //通过程序的包名创建URI
        val packageURI = Uri.parse("package:" + packageName)
        //创建Intent意图
        val intent = Intent(Intent.ACTION_DELETE, packageURI)
        //执行卸载程序
        context.startActivity(intent)
    }

    /** 检查手机上是否安装了指定的软件  */
    fun isAvailable(context: Context, packageName: String): Boolean {
        // 获取packagemanager
        val packageManager = context.packageManager
        // 获取所有已安装程序的包信息
        val packageInfos = packageManager.getInstalledPackages(0)
        // 用于存储所有已安装程序的包名
        val packageNames = ArrayList<String>()
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (i in packageInfos.indices) {
                val packName = packageInfos[i].packageName
                packageNames.add(packName)
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName)
    }

    /** 检查手机上是否安装了指定的软件  */
    fun isAvailable(context: Context, file: File): Boolean {
        return isAvailable(context, getPackageName(context, file.absolutePath))
    }

    /** 根据文件路径获取包名  */
    fun getPackageName(context: Context, filePath: String): String? {
        val packageManager = context.packageManager
        val info = packageManager.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES)
        if (info != null) {
            val appInfo = info.applicationInfo
            return appInfo.packageName  //得到安装包名称
        }
        return null
    }

    /** 从apk中获取版本信息  */
    fun getChannelFromApk(context: Context, channelPrefix: String): String {
        //从apk包中获取
        val appinfo = context.applicationInfo
        val sourceDir = appinfo.sourceDir
        //默认放在meta-inf/里， 所以需要再拼接一下
        val key = "META-INF/" + channelPrefix
        var ret = ""
        var zipfile: ZipFile? = null
        try {
            zipfile = ZipFile(sourceDir)
            val entries = zipfile.entries()
            while (entries.hasMoreElements()) {
                val entryName = entries.nextElement().name
                if (entryName.startsWith(key)) {
                    ret = entryName
                    break
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        val split = ret.split(channelPrefix.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var channel = ""
        if (split.size >= 2) {
            channel = ret.substring(key.length)
        }
        return channel
    }
}
