package com.apanda.base.base.baseapp

import android.content.Context

/**
 * App内存缓存
 */
class AppCache private constructor() {
    private val context: Context? = null//应用实例
    var token: String? = null
    var userId = "10000"
    var userName = "锋"
    var icon = "Image/20160819/1471570856669.jpeg"

    companion object {
        @Volatile private var instance: AppCache? = null
        fun getInstance(): AppCache {
            if (null == instance) {
                synchronized(AppCache::class.java) {
                    if (instance == null) {
                        instance = AppCache()
                    }
                }
            }
            return instance
        }
    }
}
