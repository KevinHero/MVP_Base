package com.apanda.base.Utils

import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.Enumeration

/**
 * des:ip管理
 * Created by xsf
 * on 2016.06.16:27
 */
object IpUtils {
    /**
     * 获取ip地址方法
     * @return
     */
    fun GetHostIp(): String? {
        try {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val ipAddr = intf.inetAddresses
                while (ipAddr.hasMoreElements()) {
                    val inetAddress = ipAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString()
                    }

                }
            }
        } catch (ex: SocketException) {
        } catch (e: Exception) {
        }

        return null
    }
}
