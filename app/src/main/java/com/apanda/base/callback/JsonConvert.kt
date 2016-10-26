package com.apanda.base.callback

import com.google.gson.stream.JsonReader

import com.lzy.okgo.convert.Converter

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

import okhttp3.Response

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：16/9/11
 * 描    述：
 * 修订历史：
 * ================================================
 */
open class JsonConvert<T> : Converter<T> {

    private var type: Type? = null

    fun setType(type: Type) {
        this.type = type
    }

    @Throws(Exception::class)
    override fun convertSuccess(response: Response): T {
        val jsonReader = JsonReader(response.body().charStream())

        if (type == null) {
            //以下代码是通过泛型解析实际参数,泛型必须传
            val genType = javaClass.genericSuperclass
            val params = (genType as ParameterizedType).actualTypeArguments
            type = params[0]
        }

        //noinspection unchecked
        return Convert.fromJson<Any>(jsonReader, type) as T

    }
}