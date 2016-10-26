package com.apanda.base.Module.Gank.activity

import android.app.DatePickerDialog
import android.os.Message
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.apanda.base.Module.Gank.bean.HistoryTodayBean
import com.apanda.base.R
import com.apanda.base.Utils.DateTime
import com.apanda.base.Utils.DialogUtils
import com.apanda.base.Widget.NestedListView
import com.apanda.base.base.BaseActivity
import com.apanda.base.base.BaseModel
import com.apanda.base.base.BasePresenter
import com.bumptech.glide.Glide
import java.util.*

class HistoryTodayActivity : BaseActivity<BasePresenter<*, *>, BaseModel>() {


    private var lv_hs_today: NestedListView? = null

    override fun refresh(msg: Message) {

    }

    override val layoutId: Int
        get() = R.layout.activity_history_today

    override fun initPresenter() {

    }

    override fun initView() {

        startProgressDialog()
        lv_hs_today = findViewById(R.id.lv_hs_today) as NestedListView

        val dateString = DateTime.dateString
        requestdata(dateString.replace("/", "").substring(4))

        val toolbar = findViewById(R.id.toolbar) as Toolbar


        toolbar.title = "历史上的今天"
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        setSupportActionBar(toolbar)


        toolbar.setNavigationIcon(R.mipmap.ic_back)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.setOnMenuItemClickListener { item ->
            val calendar = Calendar.getInstance()
            val dateString: String
            when (item.itemId) {
                R.id.his_today_up -> {
                    //TODO 收藏

                    dateString = DateTime.formatTime(DateTime.beforeNDays(-1)).split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].replace("-", "").substring(4)
                    requestdata(dateString)
                }
                R.id.his_today_choose -> {
                    //TODO 收藏
                    val pickerDialog = DatePickerDialog(_instance, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        val s = "0" + (monthOfYear + 1) + "" + if (dayOfMonth <= 9) "0" + dayOfMonth else dayOfMonth
                        requestdata(if (s.length == 5) s.substring(1) else s)
                    }, calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH))
                    pickerDialog.show()
                }
                R.id.his_today_down -> {
                    //TODO 收藏
                    dateString = DateTime.formatTime(DateTime.beforeNDays(1)).split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].replace("-", "").substring(4)
                    requestdata(dateString)
                }
            }


            true
        }

    }

    private fun requestdata(data: String) {
        object : Thread() {
            override fun run() {
                //
                //                String res = new ShowApiRequest("http://route.showapi.com/119-42", "13550", "8097738f0cc84ccfa4ba16426b019a85")
                //                        .addTextPara("date", data)
                //                        .post();
                //                L.d(TAG, res);
                //
                //                final String showapi_res_body = GsonUtils.getNoteJsonString(res, "showapi_res_body");
                //                final String body = GsonUtils.getNoteJsonString(showapi_res_body, "list");
                //
                //                final List<HistoryTodayBean> list = GsonUtils.parserJsonToArrayBeans(showapi_res_body, "list", HistoryTodayBean.class);
                //
                //                runOnUiThread(new Runnable() {
                //                    @Override
                //                    public void run() {
                //                        lv_hs_today.setAdapter(new HisTodayAdapter(list));
                //                    }
                //                });
            }
        }.start()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return true
    }


    private inner class HisTodayAdapter(list: List<HistoryTodayBean>) : BaseAdapter() {

        internal var list: List<HistoryTodayBean> = ArrayList()

        init {
            stopProgressDialog()
            this.list = list
        }

        override fun getCount(): Int {
            return list.size
        }

        override fun getItem(position: Int): HistoryTodayBean {
            return list[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
            var convertView = convertView

            convertView = View.inflate(_instance, R.layout.ios_cardview, null)

            val viewHolder = ViewHolder(convertView)

            val todayBean = getItem(position)

            viewHolder.tv_title.text = todayBean.title
            if (todayBean.img == null) {
                //                viewHolder.iv_title.setVisibility(View.INVISIBLE);
                //                Glide.with(mContext).load("http://ww1.sinaimg.cn/mw690/67eaffd3gw1e9mqvlkcl9g2046046mz9.gif").centerCrop().into(viewHolder.iv_title);
            } else {
                Glide.with(_instance).load(todayBean.img).centerCrop().into(viewHolder.iv_title)

            }

            viewHolder.tv_date.text = todayBean.year + "/" + todayBean.month + "/" + todayBean.day


            convertView.setOnLongClickListener {
                val dialog = DialogUtils.showAlert(R.layout.imageview, _instance)
                val viewById = dialog.findViewById(R.id.iv_pic) as ImageView
                val name = dialog.findViewById(R.id.tv_name) as TextView
                val time = dialog.findViewById(R.id.tv_time) as TextView


                if (todayBean.img == null) {
                    //                viewHolder.iv_title.setVisibility(View.INVISIBLE);
                    Glide.with(_instance).load("http://ww4.sinaimg.cn/mw690/67eaffd3gw1dsrx9qjv4yg.gif").centerCrop().into(viewById)
                } else {
                    Glide.with(_instance).load(todayBean.img).fitCenter().into(viewById)

                }


                Glide.with(_instance).load(todayBean.img).fitCenter().into(viewById)
                name.text = todayBean.title
                name.setTextColor(getColor(R.color.colorPrimary))
                time.setTextColor(getColor(R.color.colorPrimary))
                time.text = todayBean.year + "/" + todayBean.month + "/" + todayBean.day

                true
            }


            return convertView
        }

        inner class ViewHolder(var rootView: View) {
            var iv_title: ImageView
            var tv_title: TextView
            var tv_date: TextView

            init {
                this.iv_title = rootView.findViewById(R.id.iv_title) as ImageView
                this.tv_title = rootView.findViewById(R.id.tv_title) as TextView
                this.tv_date = rootView.findViewById(R.id.tv_date) as TextView
            }

        }
    }
}
