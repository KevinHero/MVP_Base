package com.apanda.base.Utils

/**
 * 集合操作工具类

 */
object CollectionUtils {

    /**
     * 判断集合是否为null或者0个元素

     * @param c
     * *
     * @return
     */
    fun isNullOrEmpty(c: Collection<*>?): Boolean {
        return null == c || c.isEmpty()
    }
}
