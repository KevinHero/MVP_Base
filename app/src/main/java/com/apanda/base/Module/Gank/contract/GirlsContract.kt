package com.apanda.base.Module.Gank.contract


import com.apanda.base.base.BaseModel
import com.apanda.base.base.BasePresenter
import com.apanda.base.base.BaseView

class GirlsContract {
    interface Model : BaseModel

    interface View : BaseView

    abstract class Presenter : BasePresenter<View, Model>()

}
