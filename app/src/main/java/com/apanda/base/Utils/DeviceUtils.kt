package com.apanda.base.Utils

import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build

import java.io.IOException
import java.io.InputStreamReader
import java.io.LineNumberReader

/**
 *
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/8/1
 * desc  : 设备相关工具类
 *
 */
class DeviceUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't fuck me...")
    }

    companion object {

        /**
         * 获取设备MAC地址
         *
         * 需添加权限 `&lt;uses-permission android:name=&quot;android.permission.ACCESS_WIFI_STATE&quot;/&gt;`

         * @param context 上下文
         * *
         * @return MAC地址
         */
        fun getMacAddress(context: Context): String {
            val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val info = wifi.connectionInfo
            val macAddress = info.macAddress.replace(":", "")
            return macAddress ?: ""
        }

        /**
         * 获取设备MAC地址

         *
         * 需添加权限 `&lt;uses-permission android:name=&quot;android.permission.ACCESS_WIFI_STATE&quot;/&gt;`

         * @return MAC地址
         */
        val macAddress: String
            get() {
                var macAddress: String? = null
                var reader: LineNumberReader? = null
                try {
                    val pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address")
                    val ir = InputStreamReader(pp.inputStream)
                    reader = LineNumberReader(ir)
                    macAddress = reader.readLine().replace(":", "")
                } catch (ex: IOException) {
                    ex.printStackTrace()
                } finally {
                    try {
                        if (reader != null) reader.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
                return if (macAddress == null) "" else macAddress
            }

        /**
         * 获取设备厂商，如Xiaomi

         * @return 设备厂商
         */
        val manufacturer: String
            get() = Build.MANUFACTURER

        /**
         * 获取设备型号，如MI2SC

         * @return 设备型号
         */
        val model: String
            get() {
                var model: String? = Build.MODEL
                if (model != null) {
                    model = model.trim { it <= ' ' }.replace("\\s*".toRegex(), "")
                } else {
                    model = ""
                }
                return model
            }
    }
}
