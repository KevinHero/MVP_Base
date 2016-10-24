package com.apanda.base.Module.Gank.presenter;


import com.apanda.base.Module.Gank.bean.ItemsBean;
import com.apanda.base.Module.Gank.contract.ItemContract;
import com.apanda.base.base.baserx.RxSubscriber;

public class ItemPresenter extends ItemContract.Presenter {

    public void loadBean(String type) {
        mRxManage.add(mModel.loadCatoBean(type).subscribe(new RxSubscriber<ItemsBean>(mContext, false) {
            @Override
            public void onStart() {
                mView.showLoading("请稍后...");
                super.onStart();
            }

            @Override
            protected void _onNext(ItemsBean _gankBean) {
                mView.returnItemsBean(_gankBean);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}