package com.apanda.base.Module.Gank.presenter


import com.apanda.base.Module.Gank.bean.GilrsBean
import com.apanda.base.Module.Gank.contract.FragmentGirlsContract
import com.apanda.base.base.baserx.RxSubscriber

class FragmentGirlsPresenter : FragmentGirlsContract.Presenter() {

    fun loadGrilsBean() {
        mRxManage.add(mModel.loadGrilsBean().subscribe(object : RxSubscriber<GilrsBean>(mContext!!, false) {
            override fun onStart() {
                mView.showLoading("请稍后...")
                super.onStart()
            }

            override fun _onNext(_gankBean: GilrsBean) {
                mView.returnGrilsBean(_gankBean)
                mView.stopLoading()
            }

            override fun _onError(message: String) {
                mView.showErrorTip(message)
            }
        }))
    }
}