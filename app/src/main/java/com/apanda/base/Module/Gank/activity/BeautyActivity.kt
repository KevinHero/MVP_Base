package com.apanda.base.Module.Gank.activity

import android.os.Message
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.ListView
import com.apanda.base.R
import com.apanda.base.base.BaseActivity
import com.apanda.base.base.BaseModel
import com.apanda.base.base.BasePresenter

class BeautyActivity : BaseActivity<BasePresenter<*, *>, BaseModel>() {


    private var toolbar: Toolbar? = null
    private var lv_hs_today: ListView? = null


    override fun refresh(msg: Message) {

    }

    override val layoutId: Int
        get() = R.layout.activity_beauty

    override fun initPresenter() {

    }


    private fun requestGirls() {
        /* new Thread() {
            @Override
            public void run() {
                String res = new ShowApiRequest("http://route.showapi.com/197-1", "13550", "8097738f0cc84ccfa4ba16426b019a85")

                        .addTextPara("num", "20")
                        .addTextPara("page", "1")
                        .post();
                System.out.println(res);

                final List<NewListBean> newslist = GsonUtils.parserJsonToArrayBeans(GsonUtils.getNoteJsonString(res, "showapi_res_body"), "newslist", NewListBean.class);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        lv_hs_today.setAdapter(new BeautyAdater(mContext, newslist));

                        dissmissDialog();
                    }
                });

            }
        }.start();*/
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.huaban, menu)
        return false
    }

    override fun initView() {

        requestGirls()

        toolbar = findViewById(R.id.toolbar) as Toolbar
        lv_hs_today = findViewById(R.id.lv_hs_today) as ListView
        toolbar!!.title = "美女"

        toolbar!!.setTitleTextColor(resources.getColor(R.color.white))
        setSupportActionBar(toolbar)

        //    toolbar.setLeft(R.drawable.star);

        //        toolbar.setNavigationIcon( );
        toolbar!!.setNavigationIcon(R.mipmap.ic_back)
        toolbar!!.setNavigationOnClickListener { finish() }
        toolbar!!.setOnMenuItemClickListener { true }


    }

}
