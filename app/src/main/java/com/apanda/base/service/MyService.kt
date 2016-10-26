package com.apanda.base.service

import android.app.Service
import android.content.Intent
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.telephony.SmsManager

class MyService : Service() {
    private var locationManager: LocationManager? = null
    private var myLocationListener: MyLocationListener? = null

    override fun onBind(intent: Intent): IBinder? {
        // TODO Auto-generated method stub
        return null
    }

    override fun onCreate() {
        super.onCreate()

        // 1.获取位置的管理者
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // 2.获取定位方式
        // 2.1获取所有的定位方式，true:表示返回所有可用定位方式
        val providers = locationManager!!.getProviders(true)
        for (string in providers) {
            println(string)
        }
        // 2.2获取最佳的定位方式
        val criteria = Criteria()
        criteria.isAltitudeRequired = true// 设置是否可以定位海拔,如果设置定位海拔，返回一定是gps
        // criteria : 设置定位属性
        // enabledOnly : true如果定位可用就返回
        val bestProvider = locationManager!!.getBestProvider(criteria, true)
        println("最佳的定位方式:" + bestProvider)
        // 3.定位
        myLocationListener = MyLocationListener()
        // provider : 定位的方式
        // minTime : 定位的最小时间间隔
        // minDistance : 定位最小的间隔距离
        // LocationListener : 定位监听
        locationManager!!.requestLocationUpdates(bestProvider, 0, 0f,
                myLocationListener)
    }

    private inner class MyLocationListener : LocationListener {
        //当定位位置改变的调用的方法
        //Location : 当前的位置
        override fun onLocationChanged(location: Location) {
            location.accuracy//获取精确位置
            location.altitude//获取海拔
            val latitude = location.latitude//获取纬度，平行
            val longitude = location.longitude//获取经度，垂直

            //发送坐标给指定手机号码
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage("5556", null, "longitude:$longitude latitude:$latitude", null, null)

            //停止服务,但是必须得是startservice开启
            stopSelf()

        }

        //当定位状态发生改变的时候调用的方式
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            // TODO Auto-generated method stub

        }

        //当定位可用的时候调用的方法
        override fun onProviderEnabled(provider: String) {
            // TODO Auto-generated method stub

        }

        //当定位不可用的时候调用的方法
        override fun onProviderDisabled(provider: String) {
            // TODO Auto-generated method stub

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager!!.removeUpdates(myLocationListener)//关闭gps,但是高版本中规定打开和关闭gps必须由用户自己主观的去实现，代码已经不允许进行操作
    }

}
