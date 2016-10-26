package com.apanda.base.callback

import android.text.TextUtils

import com.google.gson.Gson
import com.lzy.okgo.callback.AbsCallback
import com.lzy.okgo.request.BaseRequest

import org.json.JSONObject

import java.lang.reflect.Type

import okhttp3.Response

/**
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端
 * 包名:
 * 作者: created by 熊凯 on 2016/9/29 11:10
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: JsonCallback
 */
abstract class JsonCallback<T> : AbsCallback<T> {

    private val clazz: Class<T>?
    private val type: Type?

    /**
     * 传class,直接返回解析生成的对象
     */
    constructor(clazz: Class<T>) {
        this.clazz = clazz
    }

    /**
     * 对于需要返回集合类型的,可以传type
     * type = new TypeToken>(){}.getType()
     */
    constructor(type: Type) {
        this.type = type
    }

    override fun onBefore(request: BaseRequest<*>?) {
        super.onBefore(request)
        //主要用于在所有请求之前添加公共的请求头或请求参数，例如登录授权的 token,使用的设备信息等,可以随意添加,也可以什么都不传
        //        request.headers("header1", "HeaderValue1")//
        //                .params("params1", "ParamsValue1")//
        //                .params("token", "3215sdf13ad1f65asd4f3ads1f");
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */

    @Throws(Exception::class)
    fun parseNetworkResponse(response: Response): T? {
        val responseData = response.body().string()
        if (TextUtils.isEmpty(responseData)) return null

        /**
         * 一般来说，服务器返回的响应码都包含 code，msg，data 三部分，在此根据自己的业务需要完成相应的逻辑判断
         * 以下只是一个示例，具体业务具体实现
         */
        val jsonObject = JSONObject(responseData)
        val msg = jsonObject.optString("msg", "")
        val code = jsonObject.optInt("code", 0)
        val data = jsonObject.optString("data", "")
        when (code) {
            0 -> {
                /**
                 * 假如 code = 0 代表成功，这里默认实现了Gson解析,可以自己替换成fastjson等
                 * clazz类型就是解析javaBean
                 * type类型就是解析List
                 */
                if (clazz == String::class.java) return data as T
                if (clazz != null) return Gson().fromJson(data, clazz)
                if (type != null) return Gson().fromJson<T>(data, type)
            }
            104 ->
                //比如：用户授权信息无效，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw IllegalStateException("用户授权信息无效")
            105 ->
                //比如：用户收取信息已过期，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw IllegalStateException("用户收取信息已过期")
            106 ->
                //比如：用户账户被禁用，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw IllegalStateException("用户账户被禁用")
            300 ->
                //比如：其他乱七八糟的等，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw IllegalStateException("其他乱七八糟的等")
            else -> throw IllegalStateException("错误代码：$code，错误信息：$msg")
        }
        throw IllegalStateException("数据解析错误")
    }
}