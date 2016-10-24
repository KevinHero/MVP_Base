package com.apanda.base.Module.Gank.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apanda.base.Module.Gank.bean.ItemsBean;
import com.apanda.base.R;
import com.apanda.base.Utils.GlideImageLoader;
import com.apanda.base.Widget.WebviewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by apanda on 2016/10/24.
 */
public class ItemAdapter extends RecyclerView.Adapter {

    private final LayoutInflater mLayoutInflater;
    private final Activity mContext;

    private final List<ItemsBean.ResultsBean> _results = new ArrayList<>();

    public ItemAdapter(Activity _activity, List<ItemsBean.ResultsBean> _results) {
        mContext = _activity;
        mLayoutInflater = LayoutInflater.from(_activity);
        if (_results.size() > 0) {
            this._results.clear();
            this._results.addAll(_results);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(mLayoutInflater.inflate(R.layout.ios_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;

        ItemsBean.ResultsBean bean = _results.get(position);
        List<String> images = bean.images;
        if (images != null && images.size() > 0) {
            itemHolder._IvTitle.setVisibility(View.VISIBLE);
            GlideImageLoader.getInstance().onDisplayImage(mContext, itemHolder._IvTitle, images.get(0));
        }else{
            itemHolder._IvTitle.setVisibility(View.GONE);
        }
        itemHolder._TvTitle.setText(bean.desc);
        itemHolder._TvDate.setText(bean.createdAt.split("T")[0]);

    }

    @Override
    public int getItemCount() {
        return _results.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_title)
        ImageView _IvTitle;
        @Bind(R.id.tv_title)
        TextView _TvTitle;
        @Bind(R.id.tv_date)
        TextView _TvDate;

        ItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemsBean.ResultsBean resultsBean = _results.get(getPosition());
                    WebviewActivity.startActivity(mContext, resultsBean.url, resultsBean.desc);
                }
            });
        }
    }
}
