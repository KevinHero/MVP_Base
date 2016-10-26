package com.apanda.base.Utils


import com.google.gson.Gson
import com.google.gson.JsonNull
import com.google.gson.JsonSyntaxException

import org.json.JSONObject

import java.lang.reflect.Type

/**
 * JSON解析二次封装

 */
object JsonUtils {

    // 采取单例模式
    private val gson = Gson()

    /**
     * @param src :将要被转化的对象
     * *
     * @return :转化后的JSON串
     * *
     * @MethodName : toJson
     * *
     * @Description : 将对象转为JSON串，此方法能够满足大部分需求
     */
    fun toJson(src: Any?): String? {
        if (null == src) {
            return gson.toJson(JsonNull.INSTANCE)
        }
        try {
            return gson.toJson(src)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * @param json
     * *
     * @param classOfT
     * *
     * @return
     * *
     * @MethodName : fromJson
     * *
     * @Description : 用来将JSON串转为对象，但此方法不可用来转带泛型的集合
     */
    fun <T> fromJson(json: String, classOfT: Class<T>): Any? {
        try {
            return gson.fromJson<Any>(json, classOfT as Type)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * @param json
     * *
     * @param typeOfT
     * *
     * @return
     * *
     * @MethodName : fromJson
     * *
     * @Description : 用来将JSON串转为对象，此方法可用来转带泛型的集合，如：Type为 new
     * * TypeToken>(){}.getType()
     * * ，其它类也可以用此方法调用，就是将List替换为你想要转成的类
     */
    fun fromJson(json: String, typeOfT: Type): Any? {
        try {
            return gson.fromJson<Any>(json, typeOfT)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 获取json中的某个值

     * @param json
     * *
     * @param key
     * *
     * @return
     */
    fun getValue(json: String, key: String): String? {
        try {
            val `object` = JSONObject(json)
            return `object`.getString(key)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 获取json中的list值

     * @param json
     * *
     * @return
     */
    fun getListValue(json: String): String? {
        try {
            val `object` = JSONObject(json)
            return `object`.getString("list")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun getDoubleValue(json: String, key: String): Double? {
        try {
            val `object` = JSONObject(json)
            return `object`.getDouble(key)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun getIntValue(json: String, key: String): Int {
        try {
            val `object` = JSONObject(json)
            return `object`.getInt(key)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0
    }

}
