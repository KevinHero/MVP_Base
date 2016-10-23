package com.apanda.base.Module.Gank.io.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.apanda.base.Module.Gank.io.bean.ItemBean;
import com.apanda.base.Module.Gank.io.customview.WebviewActivity;
import com.apanda.base.R;

import java.util.List;

/**
 * Created by kai.xiong on 2016/3/26.
 */
public class ListViewAdapter extends BaseAdapter {

    List<ItemBean> dayBean;
    private Context mContext;

    public ListViewAdapter(Context context, List<ItemBean> dayBean) {
        this.dayBean = dayBean;
        mContext = context;

    }

    @Override
    public int getCount() {
        return dayBean.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        final ItemBean itemBeans = dayBean.get(position);

        convertView = View.inflate(mContext, R.layout.android_item, null);
        TextView textView = (TextView) convertView.findViewById(R.id.tv_item_tips);
        final TextView tv_read_status = (TextView) convertView.findViewById(R.id.tv_read_status);
        textView.setText(itemBeans.desc);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WebviewActivity.startActivity(mContext, itemBeans.url, itemBeans.type);
                tv_read_status.setVisibility(View.VISIBLE);
            }
        });

        return convertView;
    }
}
