package com.apanda.base.Module.Gank.presenter


import com.apanda.base.Entity.GankBean
import com.apanda.base.Module.Gank.bean.DayBean
import com.apanda.base.Module.Gank.bean.HisBean
import com.apanda.base.Module.Gank.contract.GankContract
import com.apanda.base.base.baserx.RxSubscriber

/**
 * Created by apanda on 2016/10/21.
 */

class GankPresenter : GankContract.Presenter() {
    override fun lodeGankBeanRequest() {

        mRxManage.add(mModel.lodeGankBean().subscribe(object : RxSubscriber<GankBean>(mContext!!, false) {
            override fun onStart() {
                super.onStart()
                //   mView.showLoading(mContext.getString(R.string.loading));
            }

            override fun _onNext(_gankBean: GankBean) {
                mView.returnGankBean(_gankBean)
                //  mView.stopLoading();
            }

            override fun _onError(message: String) {
                // mView.showErrorTip(message);
            }
        }))

    }

    override fun loadDayBean(_history: String) {

        mRxManage.add(mModel.loadDayBean(_history).subscribe(object : RxSubscriber<DayBean>(mContext!!, false) {
            override fun onStart() {
                super.onStart()
                // mView.showLoading(mContext.getString(R.string.loading));
            }

            override fun _onNext(_dayBean: DayBean) {
                mView.returnDayBean(_dayBean)
                //  mView.stopLoading();
            }


            override fun _onError(message: String) {
                //mView.showErrorTip(message);
            }
        }))
    }

    fun loadHistoty() {

        mRxManage.add(mModel.loadHistory().subscribe(object : RxSubscriber<HisBean>(mContext!!, false) {
            override fun onStart() {
                super.onStart()
                //   mView.showLoading(mContext.getString(R.string.loading));
            }

            override fun _onNext(_s: HisBean) {
                mView.returnHistory(_s.results!![0].replace("-", "/"))
                // mView.stopLoading();
            }


            override fun _onError(message: String) {
                //mView.showErrorTip(message);
            }
        }))


    }
}
