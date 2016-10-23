package com.apanda.base.Module.Gank.presenter;


import com.apanda.base.Entity.GankBean;
import com.apanda.base.Module.Gank.contract.GankContract;
import com.apanda.base.R;
import com.apanda.base.base.baserx.RxSubscriber;

/**
 * Created by apanda on 2016/10/21.
 */

public class GankPresenter extends GankContract.Presenter {
    @Override
    public void lodeGankBeanRequest() {

        mRxManage.add(mModel.lodeGankBean().subscribe(new RxSubscriber<GankBean>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(GankBean _gankBean) {
                mView.returnGankBean(_gankBean);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));


    }
}
