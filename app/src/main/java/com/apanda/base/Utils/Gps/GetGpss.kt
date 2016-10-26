///**
// *
// */
//package com.apanda.base.Utils.Gps;
//
//import android.content.Context;
//import android.text.TextUtils;
//
//
//
//import java.text.SimpleDateFormat;
//import java.util.Locale;
//
//
//public class GetGpss {
//    /**
//     * 开始定位
//     */
//    public final static int MSG_LOCATION_START = 0;
//    /**
//     * 定位完成
//     */
//    public final static int MSG_LOCATION_FINISH = 1;
//    /**
//     * 停止定位
//     */
//    public final static int MSG_LOCATION_STOP = 2;
//
//    public final static String KEY_URL = "URL";
//    public final static String URL_H5LOCATION = "file:///android_asset/location.html";
//
//    private static GetGpss _singleInstance = null;
//
//    private GetGpss() {
//    }
//
//    public static GetGpss getInstance() {
//        synchronized (GetGpss.class) {
//            if (_singleInstance == null) {
//                _singleInstance = new GetGpss();
//            }
//        }
//        return _singleInstance;
//    }
//
//
//    //定位
//    public void initLoc(Context instance) {
//        //初始化定位
//        AMapLocationClient mlocationClient = new AMapLocationClient(instance);
//        //设置定位回调监听
//        mlocationClient.setLocationListener(new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation _aMapLocation) {
//                setLocation(_aMapLocation);
//
//            }
//        });
//        //初始化定位参数
//        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
//        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        //设置是否返回地址信息（默认返回地址信息）
//        mLocationOption.setNeedAddress(true);
//        //设置是否只定位一次,默认为false
//        mLocationOption.setOnceLocation(true);
//        //设置是否强制刷新WIFI，默认为强制刷新
//        mLocationOption.setWifiActiveScan(true);
//        //设置是否允许模拟位置,默认为false，不允许模拟位置
//        mLocationOption.setMockEnable(true);
//        //设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(2000);
//        //给定位客户端对象设置定位参数
//        mlocationClient.setLocationOption(mLocationOption);
//        mlocationClient.startLocation();
//    }
//
//    private MyLocationListener _locationListener;
//
//    public interface MyLocationListener {
//        void getLocation(AMapLocation _aMapLocation);
//    }
//
//    private void setLocation(AMapLocation _aMapLocation) {
//        _locationListener.getLocation(_aMapLocation);
//    }
//
//
//    public void setMyLocationListener(MyLocationListener listener) {
//        this._locationListener = listener;
//    }
//
//
//    /**
//     * 根据定位结果返回定位信息的字符串
//     *
//     * @param location
//     * @return
//     */
//    public synchronized String getLocationStr(AMapLocation location) {
//        if (null == location) {
//            return null;
//        }
//        StringBuffer sb = new StringBuffer();
//        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
//        if (location.getErrorCode() == 0) {
//            sb.append("定位成功" + "\n");
//            sb.append("定位类型: " + location.getLocationType() + "\n");
//            sb.append("经    度    : " + location.getLongitude() + "\n");
//            sb.append("纬    度    : " + location.getLatitude() + "\n");
//            sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
//            sb.append("提供者    : " + location.getProvider() + "\n");
//
//            if (location.getProvider().equalsIgnoreCase(
//                    android.location.LocationManager.GPS_PROVIDER)) {
//                // 以下信息只有提供者是GPS时才会有
//                sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
//                sb.append("角    度    : " + location.getBearing() + "\n");
//                // 获取当前提供定位服务的卫星个数
//                sb.append("星    数    : "
//                        + location.getSatellites() + "\n");
//            } else {
//                // 提供者是GPS时是没有以下信息的
//                sb.append("国    家    : " + location.getCountry() + "\n");
//                sb.append("省            : " + location.getProvince() + "\n");
//                sb.append("市            : " + location.getCity() + "\n");
//                sb.append("城市编码 : " + location.getCityCode() + "\n");
//                sb.append("区            : " + location.getDistrict() + "\n");
//                sb.append("区域 码   : " + location.getAdCode() + "\n");
//                sb.append("地    址    : " + location.getAddress() + "\n");
//                sb.append("兴趣点    : " + location.getPoiName() + "\n");
//                //定位完成的时间
//                sb.append("定位时间: " + formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
//            }
//        } else {
//            //定位失败
//            sb.append("定位失败" + "\n");
//            sb.append("错误码:" + location.getErrorCode() + "\n");
//            sb.append("错误信息:" + location.getErrorInfo() + "\n");
//            sb.append("错误描述:" + location.getLocationDetail() + "\n");
//        }
//        //定位之后的回调时间
//        sb.append("回调时间: " + formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");
//        return sb.toString();
//    }
//
//    private static SimpleDateFormat sdf = null;
//
//    public synchronized static String formatUTC(long l, String strPattern) {
//        if (TextUtils.isEmpty(strPattern)) {
//            strPattern = "yyyy-MM-dd HH:mm:ss";
//        }
//        if (sdf == null) {
//            try {
//                sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
//            } catch (Throwable e) {
//            }
//        } else {
//            sdf.applyPattern(strPattern);
//        }
//        return sdf == null ? "NULL" : sdf.format(l);
//    }
//}
