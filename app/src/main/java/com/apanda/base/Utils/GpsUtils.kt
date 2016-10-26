package com.apanda.base.Utils

import android.content.Context
import android.location.LocationManager
import android.provider.Settings

/**
 * Created by Android on 2016/9/29.
 */

object GpsUtils {

    fun GPSOpened(instance: Context): Boolean {
        return Settings.Secure.isLocationProviderEnabled(instance.contentResolver,
                LocationManager.GPS_PROVIDER)
    }

}