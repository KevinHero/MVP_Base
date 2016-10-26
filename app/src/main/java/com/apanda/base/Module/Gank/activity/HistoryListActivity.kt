package com.apanda.base.Module.Gank.activity

import android.content.Context
import android.content.Intent
import android.os.Message
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.apanda.base.Module.Gank.bean.ItemBean
import com.apanda.base.R
import com.apanda.base.Widget.WebviewActivity
import com.apanda.base.base.BaseActivity
import com.apanda.base.base.BaseModel
import com.apanda.base.base.BasePresenter
import com.bumptech.glide.Glide

class HistoryListActivity :  BaseActivity<BasePresenter<*, *>, BaseModel>() {




    override fun refresh(msg: Message) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var toolbar: Toolbar? = null
    private var his_lv: ListView? = null
    private var mContext: Context? = null
    private var type: String? = null
    private var type_1: String? = null
    private var count: Int = 0
    private var way: Int = 0


    /**
     * 展示数据

     * @param
     */
    private fun setListAdapter(mBitmapBeans: List<ItemBean>, videoBeans: List<ItemBean>) {
        his_lv!!.adapter = ListViewHistoryAdapter(mBitmapBeans, videoBeans)

    }


    override val layoutId: Int
        get() = R.layout.activity_history_list

    override fun initPresenter() {

    }

    override fun initView() {


        setContentView(R.layout.activity_history_list)
        startProgressDialog()

        type = intent.getStringExtra("type")
        type_1 = intent.getStringExtra("type_1")
        count = intent.getIntExtra("count", 20)
        way = intent.getIntExtra("way", 0)
        mContext = this
        /* new Thread() {
            @Override
            public void run() {
                try {
                    final String bitmapJson = NetUtils.run("http://gank.io/api/data/" + type + "/" + count + "/1");
                    final List<ItemBean> mBitmapBeans = GsonUtils.parserJsonToArrayBeans(bitmapJson, "results", ItemBean.class);
                    final List<ItemBean> videoBeans = GsonUtils.parserJsonToArrayBeans(NetUtils.run("http://gank.io/api/data/" + type_1 + "/" + count + "/1"), "results", ItemBean.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setListAdapter(mBitmapBeans, videoBeans);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();*/


        toolbar = findViewById(R.id.toolbar) as Toolbar
        his_lv = findViewById(R.id.his_lv) as ListView
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        if (way == 1) {
            toolbar.logoDescription = "往期精彩"
            toolbar.title = "往期精彩"
        } else {
            toolbar.title = type_1
            toolbar.logoDescription = type_1
        }
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        setSupportActionBar(toolbar)

        //        toolbar.setNavigationIcon( );
        toolbar.setNavigationIcon(R.mipmap.ic_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }


    private inner class ListViewHistoryAdapter(internal var mBitmapBeans: List<ItemBean>, internal var videoBeans: List<ItemBean>) : BaseAdapter() {

        override fun getCount(): Int {
            return mBitmapBeans.size
        }

        override fun getItem(position: Int): Any? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
            var convertView = convertView

            stopProgressDialog()
            convertView = View.inflate(mContext, R.layout.ios_cardview, null)


            val imageView = convertView.findViewById(R.id.iv_title) as ImageView
            val title = convertView.findViewById(R.id.tv_title) as TextView
            val date = convertView.findViewById(R.id.tv_date) as TextView


            Glide.with(mContext).load(mBitmapBeans[position].url).centerCrop().into(imageView)

            title.text = videoBeans[position].desc
            val time = mBitmapBeans[position].createdAt!!.replace("T", " ").split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]

            convertView.setOnClickListener {
                if (way == 1) {
                    //                        new Thread() {
                    //                            @Override
                    //                            public void run() {
                    //                                String run = null;
                    //                                try {
                    //                                    String dateString = mBitmapBeans.get(position).createdAt.split("T")[0].replace("-", "/");
                    //                                    run = NetUtils.run("http://gank.io/api/day/" + dateString);
                    //
                    //                                    final DayBean dayBean = GsonUtils.parserJsonToArrayBean(run, DayBean.class);
                    //
                    //                                    runOnUiThread(new Runnable() {
                    //                                        @Override
                    //                                        public void run() {
                    //                                            HistoryActivity.startActivity(mContext, dayBean);
                    //                                        }
                    //                                    });
                    //                                } catch (IOException e) {
                    //                                    e.printStackTrace();
                    //                                }
                    //                            }
                    //                        }.start();
                    //                    Log.d("SplashActivity", "onCreate: " + run);


                } else {
                    WebviewActivity.startActivity(mContext!!, videoBeans[position].url.toString(), videoBeans[position].desc.toString())
                }
            }


            date.text = time

            return convertView
        }
    }

    companion object {


        fun startActivity(mContext: Context, type: String, type_1: String, count: Int, way: Int) {
            val mIntent = Intent(mContext, HistoryListActivity::class.java)
            mIntent.putExtra("type", type)
            mIntent.putExtra("type_1", type_1)
            mIntent.putExtra("count", count)
            mIntent.putExtra("way", way)
            mContext.startActivity(mIntent)
        }
    }
}
