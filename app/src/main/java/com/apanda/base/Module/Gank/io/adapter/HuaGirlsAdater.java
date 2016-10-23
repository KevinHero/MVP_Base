package com.apanda.base.Module.Gank.io.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apanda.base.Module.Gank.io.bean.HuaGirlsBean;
import com.apanda.base.Module.Gank.io.customview.WebviewActivity;
import com.apanda.base.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kai.xiong on 2016/3/27.
 */
public class HuaGirlsAdater extends BaseAdapter {

    List<HuaGirlsBean> list = new ArrayList<>();
    Context mContext;

    public HuaGirlsAdater(Context context, List<HuaGirlsBean> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public HuaGirlsBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = View.inflate(mContext, R.layout.huagirls_cardview, null);

        ViewHolder viewHolder = new ViewHolder(convertView);

        final HuaGirlsBean todayBean = getItem(position);

        viewHolder.tv_title.setText(todayBean.title);
        if (todayBean.thumb == null) {
//                viewHolder.iv_title.setVisibility(View.INVISIBLE);
            Glide.with(mContext).load("http://ww1.sinaimg.cn/mw690/67eaffd3gw1e9mqvlkcl9g2046046mz9.gif").centerCrop().into(viewHolder.iv_title);
        } else {
            Glide.with(mContext).load(todayBean.thumb).centerCrop().into(viewHolder.iv_title);

        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebviewActivity.startActivity(mContext, todayBean.url, todayBean.title);
            }
        });


        return convertView;
    }

    public class ViewHolder {
        public View rootView;
        public ImageView iv_title;
        public TextView tv_title;
        public TextView tv_date;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_title = (ImageView) rootView.findViewById(R.id.iv_title);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_date = (TextView) rootView.findViewById(R.id.tv_date);
        }

    }

}
