package com.apanda.base.Module.Gank.bean

/**
 * Created by apanda on 2016/10/24.
 */

class ItemsBean {

    var error: Boolean = false

    var results: List<ResultsBean>? = null

    class ResultsBean {
        var _id: String? = null
        var createdAt: String? = null
        var desc: String? = null
        var publishedAt: String? = null
        var source: String? = null
        var type: String? = null
        var url: String? = null
        var used: Boolean = false
        var who: String? = null
        var images: List<String>? = null
    }
}
