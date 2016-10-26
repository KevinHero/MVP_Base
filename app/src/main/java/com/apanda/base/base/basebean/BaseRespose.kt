package com.apanda.base.base.basebean

import java.io.Serializable

/**
 * des:封装服务器返回数据
 * Created by xsf
 * on 2016.09.9:47
 */
class BaseRespose<T> : Serializable {
    var code: String? = null
    var msg: String? = null

    var data: T? = null

    fun success(): Boolean {
        return "1" == code
    }

    override fun toString(): String {
        return "BaseRespose{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}'
    }
}
