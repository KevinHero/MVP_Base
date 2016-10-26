package com.apanda.base.Module.Gank.bean

import java.io.Serializable

/**
 * Created by kai.xiong on 2016/3/26.
 */
class ItemBean : Serializable {
    var _id: String? = null
    var _ns: String? = null
    var createdAt: String? = null
    var desc: String? = null
    var publishedAt: String? = null
    var type: String? = null
    var url: String? = null
    var used: Boolean = false
    var who: String? = null
}
