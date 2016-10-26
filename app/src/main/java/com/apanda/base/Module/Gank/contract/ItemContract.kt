package com.apanda.base.Module.Gank.contract


import com.apanda.base.Module.Gank.bean.ItemsBean
import com.apanda.base.base.BaseModel
import com.apanda.base.base.BasePresenter
import com.apanda.base.base.BaseView

import rx.Observable

class ItemContract {

    interface Model : BaseModel {
        fun loadCatoBean(_type: String): Observable<ItemsBean>
    }

    interface View : BaseView {
        fun returnItemsBean(_gankBean: ItemsBean)
    }

    abstract class Presenter : BasePresenter<ItemContract.View, ItemContract.Model>()

}
