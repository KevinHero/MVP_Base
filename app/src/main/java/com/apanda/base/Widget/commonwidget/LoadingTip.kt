package com.apanda.base.Widget.commonwidget

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView

import com.apanda.base.R


/**
 * des:加载页面内嵌提示
 * Created by xsf
 * on 2016.07.17:22
 */
class LoadingTip : LinearLayout {

    private var img_tip_logo: ImageView? = null
    private var progress: ProgressBar? = null
    private var tv_tips: TextView? = null
    private var bt_operate: Button? = null
    private val errorMsg: String? = null
    private var onReloadListener: onReloadListener? = null


    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView(context)
    }

    //分为服务器失败，网络加载失败、数据为空、加载中、完成四种状态
    enum class LoadStatus {
        sereverError, error, empty, loading, finish
    }

    private fun initView(context: Context) {
        View.inflate(context, R.layout.dialog_loading_tip, this)
        img_tip_logo = findViewById(R.id.img_tip_logo) as ImageView
        progress = findViewById(R.id.progress) as ProgressBar
        tv_tips = findViewById(R.id.tv_tips) as TextView
        bt_operate = findViewById(R.id.bt_operate) as Button
        //重新尝试
        bt_operate!!.setOnClickListener {
            if (onReloadListener != null) {
                onReloadListener!!.reload()
            }
        }
        visibility = View.GONE
    }

    fun setTips(tips: String) {
        if (tv_tips != null) {
            tv_tips!!.text = tips
        }
    }

    /**
     * 根据状态显示不同的提示
     * @param loadStatus
     */
    fun setLoadingTip(loadStatus: LoadStatus) {
        when (loadStatus) {
            LoadingTip.LoadStatus.empty -> {
                visibility = View.VISIBLE
                img_tip_logo!!.visibility = View.VISIBLE
                progress!!.visibility = View.GONE
                tv_tips!!.text = context.getText(R.string.empty).toString()
                img_tip_logo!!.setImageResource(R.mipmap.no_content_tip)
            }
            LoadingTip.LoadStatus.sereverError -> {
                visibility = View.VISIBLE
                img_tip_logo!!.visibility = View.VISIBLE
                progress!!.visibility = View.GONE
                if (TextUtils.isEmpty(errorMsg)) {
                    tv_tips!!.text = context.getText(R.string.net_error).toString()
                } else {
                    tv_tips!!.text = errorMsg
                }
                img_tip_logo!!.setImageResource(R.mipmap.ic_wrong)
            }
            LoadingTip.LoadStatus.error -> {
                visibility = View.VISIBLE
                img_tip_logo!!.visibility = View.VISIBLE
                progress!!.visibility = View.GONE
                if (TextUtils.isEmpty(errorMsg)) {
                    tv_tips!!.text = context.getText(R.string.net_error).toString()
                } else {
                    tv_tips!!.text = errorMsg
                }
                img_tip_logo!!.setImageResource(R.mipmap.ic_wifi_off)
            }
            LoadingTip.LoadStatus.loading -> {
                visibility = View.VISIBLE
                img_tip_logo!!.visibility = View.GONE
                progress!!.visibility = View.VISIBLE
                tv_tips!!.text = context.getText(R.string.loading).toString()
            }
            LoadingTip.LoadStatus.finish -> visibility = View.GONE
        }
    }


    fun setOnReloadListener(onReloadListener: onReloadListener) {
        this.onReloadListener = onReloadListener
    }

    /**
     * 重新尝试接口
     */
    interface onReloadListener {
        fun reload()
    }


}

