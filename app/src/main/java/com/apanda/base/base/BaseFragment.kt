package com.apanda.base.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.apanda.base.R
import com.apanda.base.Utils.TUtil
import com.apanda.base.Utils.ToastUtils
import com.apanda.base.Widget.commonwidget.LoadingDialog
import com.apanda.base.base.baserx.RxManager

/**
 * des:基类fragment
 * Created by xsf
 * on 2016.07.12:38
 */
/***************使用例子 */

abstract class BaseFragment<T : BasePresenter<*, *>, E : BaseModel> : Fragment() {
    protected var rootView: View? = null
    var mPresenter: T? = null
    var mModel: E = null!!
    var mRxManager: RxManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null)
            rootView = inflater!!.inflate(layoutResource, container, false)
        mRxManager = RxManager()
        ButterKnife.bind(this, rootView)
        mPresenter = TUtil.getT<T>(this, 0)
        mModel = TUtil.getT<E>(this, 1)!!
        if (mPresenter != null) {
            mPresenter!!.mContext = this.activity
        }
        initPresenter()
        initView()
        return rootView
    }

    //获取布局文件
    protected abstract val layoutResource: Int

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    abstract fun initPresenter()

    //初始化view
    protected abstract fun initView()

    /**
     * 通过Class跳转界面
     */
    fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        startActivityForResult(cls, null, requestCode)
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    fun startActivityForResult(cls: Class<*>, bundle: Bundle?,
                               requestCode: Int) {
        val intent = Intent()
        intent.setClass(activity, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    @JvmOverloads fun startActivity(cls: Class<*>, bundle: Bundle? = null) {
        val intent = Intent()
        intent.setClass(activity, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }


    /**
     * 开启加载进度条
     */
    fun startProgressDialog() {
        LoadingDialog.showDialogForLoading(activity)
    }

    /**
     * 开启加载进度条

     * @param msg
     */
    fun startProgressDialog(msg: String) {
        LoadingDialog.showDialogForLoading(activity, msg, true)
    }

    /**
     * 停止加载进度条
     */
    fun stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading()
    }


    /**
     * 短暂显示Toast提示(来自String)
     */
    fun showShortToast(text: String) {
        ToastUtils.showShort(text)
    }

    /**
     * 短暂显示Toast提示(id)
     */
    fun showShortToast(resId: Int) {
        ToastUtils.showShort(resId)
    }

    /**
     * 长时间显示Toast提示(来自res)
     */
    fun showLongToast(resId: Int) {
        ToastUtils.showLong(resId)
    }

    /**
     * 长时间显示Toast提示(来自String)
     */
    fun showLongToast(text: String) {
        ToastUtils.showLong(text)
    }


    fun showToastWithImg(text: String, res: Int) {
        ToastUtils.showToastWithImg(text, res)
    }

    /**
     * 网络访问错误提醒
     */
    fun showNetErrorTip() {
        ToastUtils.showToastWithImg(getText(R.string.net_error).toString(), R.mipmap.ic_wifi_off)
    }

    fun showNetErrorTip(error: String) {
        ToastUtils.showToastWithImg(error, R.mipmap.ic_wifi_off)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ButterKnife.unbind(this)
        if (mPresenter != null)
            mPresenter!!.onDestroy()
        mRxManager.clear()
    }

}
/**
 * 通过Class跳转界面
 */
