package com.apanda.base.Utils.qiniu

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.qiniu.android.utils.StringMap

import java.lang.reflect.Type


object Json {

    fun encode(map: StringMap): String {
        return Gson().toJson(map.map())
    }

    fun encode(obj: Any): String {
        return GsonBuilder().serializeNulls().create().toJson(obj)
    }

    fun <T> decode(json: String, classOfT: Class<T>): T {
        return Gson().fromJson(json, classOfT)
    }

    fun decode(json: String): StringMap {
        // CHECKSTYLE:OFF
        val t = object : TypeToken<Map<String, Any>>() {

        }.type
        // CHECKSTYLE:ON
        val x = Gson().fromJson<Map<String, Any>>(json, t)
        return StringMap(x)
    }
}
