package com.apanda.base.base

/**
 * des:baseview
 * Created by xsf
 * on 2016.07.11:53
 */
interface BaseView {
    /*******内嵌加载 */
    fun showLoading(title: String)

    fun stopLoading()
    fun showErrorTip(msg: String)
}
