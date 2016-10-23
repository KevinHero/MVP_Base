package com.apanda.base.Module.Gank.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.apanda.base.Module.Gank.bean.ItemBean;
import com.apanda.base.Widget.WebviewActivity;
import com.apanda.base.R;
import com.apanda.base.base.BaseActivity;
import com.bumptech.glide.Glide;

import java.util.List;

public class HistoryListActivity extends BaseActivity {

    private Toolbar toolbar;
    private ListView his_lv;
    private Context mContext;
    private String type;
    private String type_1;
    private int count;
    private int way;


    public static void startActivity(Context mContext, String type, String type_1, int count, int way) {
        Intent mIntent = new Intent(mContext, HistoryListActivity.class);
        mIntent.putExtra("type", type);
        mIntent.putExtra("type_1", type_1);
        mIntent.putExtra("count", count);
        mIntent.putExtra("way", way);
        mContext.startActivity(mIntent);
    }


    /**
     * 展示数据
     *
     * @param
     */
    private void setListAdapter(List<ItemBean> mBitmapBeans, List<ItemBean> videoBeans) {
        his_lv.setAdapter(new ListViewHistoryAdapter(mBitmapBeans, videoBeans));

    }

    @Override
    protected void refresh(Message msg) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_history_list;
    }

    @Override
    public void initPresenter() {

    }

    public void initView() {


        setContentView(R.layout.activity_history_list);
        startProgressDialog();

        type = getIntent().getStringExtra("type");
        type_1 = getIntent().getStringExtra("type_1");
        count = getIntent().getIntExtra("count", 20);
        way = getIntent().getIntExtra("way", 0);
        mContext = this;
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


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        his_lv = (ListView) findViewById(R.id.his_lv);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (way == 1) {
            toolbar.setLogoDescription("往期精彩");
            toolbar.setTitle("往期精彩");
        } else {
            toolbar.setTitle(type_1);
            toolbar.setLogoDescription(type_1);
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

//        toolbar.setNavigationIcon( );
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private class ListViewHistoryAdapter extends BaseAdapter {
        List<ItemBean> mBitmapBeans;
        List<ItemBean> videoBeans;

        public ListViewHistoryAdapter(List<ItemBean> mBitmapBeans, List<ItemBean> videoBeans) {
            this.mBitmapBeans = mBitmapBeans;
            this.videoBeans = videoBeans;
        }

        @Override
        public int getCount() {
            return mBitmapBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            stopProgressDialog();
            convertView = View.inflate(mContext, R.layout.ios_cardview, null);


            final ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_title);
            final TextView title = (TextView) convertView.findViewById(R.id.tv_title);
            final TextView date = (TextView) convertView.findViewById(R.id.tv_date);


            Glide.with(mContext).load(mBitmapBeans.get(position).url).centerCrop().into(imageView);

            title.setText(videoBeans.get(position).desc);
            final String time = mBitmapBeans.get(position).createdAt.replace("T", " ").split("\\.")[0];

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                        WebviewActivity.startActivity(mContext, videoBeans.get(position).url, videoBeans.get(position).desc);
                    }
                }
            });


            date.setText(time);

            return convertView;
        }
    }
}
