package com.apanda.base.Module.Gank.activity

import android.os.Message
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.ListView
import com.apanda.base.Module.Gank.adapter.HuaGirlsAdater
import com.apanda.base.Module.Gank.bean.HuaGirlsBean
import com.apanda.base.R
import com.apanda.base.base.BaseActivity
import com.apanda.base.base.BaseModel
import com.apanda.base.base.BasePresenter
import java.util.*


/**
 * type	String	默认调用最新	34	否
 * 大胸妹=34
 * 小清新=35
 * 文艺范=36
 * 性感妹=37
 * 大长腿=38
 * 黑丝袜=39
 * 小翘臀=40
 *
 *
 * num	String	20	10	否	默认20,最大50
 * page	String	1	1	否	查询第几页
 */
class HuabanGirlActivity : BaseActivity<BasePresenter<*, *>, BaseModel>() {

    private var toolbar: Toolbar? = null
    private var lv_hs_today: ListView? = null
    private var TYPE: Int = 0
    private val mBeanList = ArrayList<HuaGirlsBean>()
    private val mAdapter: HuaGirlsAdater? = null


    override fun refresh(msg: Message) {

    }

    override val layoutId: Int
        get() = R.layout.activity_huaban_girl

    override fun initPresenter() {

    }

    private fun requestGirls(TYPE: Int) {
        /* new Thread() {
            @Override
            public void run() {
                String res = new ShowApiRequest("http://route.showapi.com/819-1", "13550", "8097738f0cc84ccfa4ba16426b019a85")
                        .addTextPara("type", String.valueOf(TYPE))
                        .addTextPara("num", "10")
                        .addTextPara("page", "1")
                        .post();
                System.out.println(res);

                final String noteJsonString = GsonUtils.getNoteJsonString(res, "showapi_res_body");
                mBeanList.clear();
                for (int i = 0; i < 10; i++) {
                    HuaGirlsBean huaGirlsBean = GsonUtils.parserJsonToArrayBean(noteJsonString, String.valueOf(i), HuaGirlsBean.class);
                    mBeanList.add(huaGirlsBean);
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new HuaGirlsAdater(HuabanGirlActivity.this, mBeanList);
                        lv_hs_today.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        dissmissDialog();
                    }
                });

            }
        }.start();*/
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.huaban, menu)
        return true
    }

    override fun initView() {
        startProgressDialog()
        TYPE = 34

        requestGirls(TYPE)

        toolbar = findViewById(R.id.toolbar) as Toolbar
        lv_hs_today = findViewById(R.id.lv_hs_today) as ListView
        toolbar!!.title = "花瓣美女"

        toolbar!!.setTitleTextColor(resources.getColor(R.color.white))
        setSupportActionBar(toolbar)

        //    toolbar.setLeft(R.drawable.star);

        //        toolbar.setNavigationIcon( );
        toolbar!!.setNavigationIcon(R.mipmap.ic_back)
        toolbar!!.setNavigationOnClickListener { finish() }
        toolbar!!.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_34 -> {
                    requestGirls(34)
                    toolbar!!.title = "花瓣美女--" + returnType(34)
                }
                R.id.action_35 -> {
                    requestGirls(35)
                    toolbar!!.title = "花瓣美女--" + returnType(35)
                }
                R.id.action_36s -> {
                    requestGirls(36)
                    toolbar!!.title = "花瓣美女--" + returnType(36)
                }
                R.id.action_37 -> {
                    requestGirls(37)
                    toolbar!!.title = "花瓣美女--" + returnType(37)
                }
                R.id.action_38 -> {
                    requestGirls(38)
                    toolbar!!.title = "花瓣美女--" + returnType(38)
                }
                R.id.action_39 -> {
                    requestGirls(39)
                    toolbar!!.title = "花瓣美女--" + returnType(39)
                }
                R.id.action_40 -> {
                    requestGirls(40)
                    toolbar!!.title = "花瓣美女--" + returnType(40)
                }
            }
            true
        }


    }


    private fun returnType(type: Int): String {
        var name = ""
        when (type) {

            34 -> name = "大胸妹"
            35 -> name = "小清新"
            36 -> name = "文艺范"
            37 -> name = "性感妹"
            38 -> name = "大长腿"
            39 -> name = "黑丝袜"
            40 -> name = "小翘臀"
        }
        return name
    }
}

