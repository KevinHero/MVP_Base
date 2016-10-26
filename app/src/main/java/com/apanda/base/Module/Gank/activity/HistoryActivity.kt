package com.apanda.base.Module.Gank.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.widget.ListView
import com.apanda.base.Module.Gank.adapter.ListViewMainAdapter
import com.apanda.base.Module.Gank.bean.DayBean
import com.apanda.base.R
import com.apanda.base.base.BaseActivity
import com.apanda.base.base.BaseModel
import com.apanda.base.base.BasePresenter
import com.apanda.base.consts.Const


class HistoryActivity :  BaseActivity<BasePresenter<*, *>, BaseModel>() {

    private var mDayBean: DayBean? = null
    private var lv_history: ListView? = null
    private var mContext: Context? = null

    override fun refresh(msg: Message) {

    }

    override val layoutId: Int
        get() = R.layout.activity_history

    override fun initPresenter() {

    }

    override fun initView() {
        mContext = this
        mDayBean = intent.getSerializableExtra("dayBean") as DayBean

        startProgressDialog()
        lv_history = findViewById(R.id.lv_history) as ListView


        Const.isHistory = true
        lv_history!!.adapter = ListViewMainAdapter(mContext as HistoryActivity, mDayBean!!, true)

        stopProgressDialog()
    }

    companion object {


        fun startActivity(context: Context, dayBean: DayBean) {
            val mIntent = Intent(context, HistoryActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("dayBean", dayBean)
            mIntent.putExtras(bundle)
            context.startActivity(mIntent)
        }
    }


}
