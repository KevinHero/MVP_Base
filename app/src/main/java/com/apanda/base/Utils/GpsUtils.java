package com.apanda.base.Utils;

import android.content.Context;
import android.location.LocationManager;
import android.provider.Settings;

/**
 * Created by Android on 2016/9/29.
 */

public class GpsUtils {

    public static boolean GPSOpened(Context instance) {
        return Settings.Secure.isLocationProviderEnabled(instance.getContentResolver(),
                LocationManager.GPS_PROVIDER);
    }

}