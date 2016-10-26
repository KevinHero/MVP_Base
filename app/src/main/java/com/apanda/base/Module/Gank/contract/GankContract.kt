package com.apanda.base.Module.Gank.contract


import com.apanda.base.Entity.GankBean
import com.apanda.base.Module.Gank.bean.DayBean
import com.apanda.base.Module.Gank.bean.HisBean
import com.apanda.base.base.BaseModel
import com.apanda.base.base.BasePresenter
import com.apanda.base.base.BaseView

import rx.Observable

/**
 * Created by apanda on 2016/10/21.
 */

class GankContract {
    interface Model : BaseModel {
        fun lodeGankBean(): Observable<GankBean>

        fun loadHistory(): Observable<HisBean>

        fun loadDayBean(_history: String): Observable<DayBean>
    }

    interface View : BaseView {
        fun returnGankBean(_gankBean: GankBean)

        fun returnHistory(History: String)

        fun returnDayBean(_dayBean: DayBean)
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun lodeGankBeanRequest()

        abstract fun loadDayBean(_history: String)
    }

}
