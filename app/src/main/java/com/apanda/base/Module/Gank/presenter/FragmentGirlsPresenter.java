package com.apanda.base.Module.Gank.presenter;


import com.apanda.base.Module.Gank.bean.GilrsBean;
import com.apanda.base.Module.Gank.contract.FragmentGirlsContract;
import com.apanda.base.base.baserx.RxSubscriber;

public class FragmentGirlsPresenter extends FragmentGirlsContract.Presenter {

    public void loadGrilsBean() {
        mRxManage.add(mModel.loadGrilsBean().subscribe(new RxSubscriber<GilrsBean>(mContext, false) {
            @Override
            public void onStart() {
                mView.showLoading("请稍后...");
                super.onStart();
            }

            @Override
            protected void _onNext(GilrsBean _gankBean) {
                mView.returnGrilsBean(_gankBean);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}