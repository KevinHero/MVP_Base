package com.apanda.base.Module.Gank.contract


import com.apanda.base.Module.Gank.bean.GilrsBean
import com.apanda.base.base.BaseModel
import com.apanda.base.base.BasePresenter
import com.apanda.base.base.BaseView

import rx.Observable

class FragmentGirlsContract {
    interface Model : BaseModel {
        fun loadGrilsBean(): Observable<GilrsBean>
    }

    interface View : BaseView {

        fun returnGrilsBean(_gankBean: GilrsBean)
    }

    abstract class Presenter : BasePresenter<View, Model>()

}
