package com.apanda.base.Module.Gank.presenter


import com.apanda.base.Module.Gank.bean.ItemsBean
import com.apanda.base.Module.Gank.contract.ItemContract
import com.apanda.base.base.baserx.RxSubscriber

class ItemPresenter : ItemContract.Presenter() {

    fun loadBean(type: String) {
        mRxManage.add(mModel.loadCatoBean(type).subscribe(object : RxSubscriber<ItemsBean>(mContext!!, false) {
            override fun onStart() {
                mView.showLoading("请稍后...")
                super.onStart()
            }

            override fun _onNext(_gankBean: ItemsBean) {
                mView.returnItemsBean(_gankBean)
                mView.stopLoading()
            }

            override fun _onError(message: String) {
                mView.showErrorTip(message)
            }
        }))
    }
}