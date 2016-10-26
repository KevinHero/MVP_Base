package com.apanda.base.base.baserx

import android.app.Activity
import android.content.Context

import com.apanda.base.R
import com.apanda.base.Utils.NetworkUtils
import com.apanda.base.Widget.commonwidget.LoadingDialog
import com.apanda.base.base.BaseApplication

import rx.Subscriber


/**
 * des:订阅封装
 * Created by xsf
 * on 2016.09.10:16
 */

/********************
 * 使用例子
 */
/*_apiService.login(mobile, verifyCode)
        .//省略
        .subscribe(new RxSubscriber<User user>(_instance,false) {
@Override
public void _onNext(User user) {
        // 处理user
        }

@Override
public void _onError(String msg) {
        ToastUtil.showShort(mActivity, msg);
        });*/
abstract class RxSubscriber<T> @JvmOverloads constructor(private val mContext: Context, private val msg: String = BaseApplication.instance.getString(R.string.loading), showDialog: Boolean = true) : Subscriber<T>() {
    private var showDialog = true

    /**
     * 是否显示浮动dialog
     */
    fun showDialog() {
        this.showDialog = true
    }

    fun hideDialog() {
        this.showDialog = true
    }

    init {
        this.showDialog = showDialog
    }

    constructor(context: Context, showDialog: Boolean) : this(context, BaseApplication.instance.getString(R.string.loading), showDialog) {
    }

    override fun onCompleted() {
        if (showDialog)
            LoadingDialog.cancelDialogForLoading()
    }

    override fun onStart() {
        super.onStart()
        if (showDialog) {
            try {
                LoadingDialog.showDialogForLoading(mContext as Activity, msg, true)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }


    override fun onNext(t: T) {
        _onNext(t)
    }

    override fun onError(e: Throwable) {
        if (showDialog)
            LoadingDialog.cancelDialogForLoading()
        e.printStackTrace()
        //网络
        if (!NetworkUtils.isAvailable(BaseApplication.instance)) {
            _onError(BaseApplication.instance.getString(R.string.no_net))
        } else if (e is ServerException) {
            _onError(e.message)
        } else {
            _onError(BaseApplication.instance.getString(R.string.net_error))
        }//其它
        //服务器
    }

    protected abstract fun _onNext(t: T)

    protected abstract fun _onError(message: String)

}
