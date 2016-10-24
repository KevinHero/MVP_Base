package com.apanda.base.Module.Gank.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apanda.base.Module.Gank.bean.GilrsBean;
import com.apanda.base.R;
import com.apanda.base.Utils.GlideImageLoader;
import com.apanda.base.Widget.imagePager.BigImagePagerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by apanda on 2016/10/24.
 */
public class GirlsAdapter extends RecyclerView.Adapter {


    private final LayoutInflater mLayoutInflater;
    private final Activity mContext;
    List<GilrsBean.ResultsBean> _results = new ArrayList<>();

    public GirlsAdapter(Context _context, List<GilrsBean.ResultsBean> _results) {
        mContext = (Activity) _context;
        mLayoutInflater = LayoutInflater.from(_context);
        //  _results.clear();
        this._results.addAll(_results);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(mLayoutInflater.inflate(R.layout.imageview, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ImageHolder iHolder = (ImageHolder) holder;
        GlideImageLoader.getInstance().onDisplayImage(mContext, iHolder._IvPic, _results.get(position).url);
        iHolder._IvPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> strings = new ArrayList<String>();
                strings.add(_results.get(position).url);
                BigImagePagerActivity.startImagePagerActivity(mContext, strings, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _results.size();
    }

    static class ImageHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_pic)
        ImageView _IvPic;
        @Bind(R.id.tv_name)
        TextView _TvName;
        @Bind(R.id.tv_time)
        TextView _TvTime;

        public ImageHolder(View _inflate) {
            super(_inflate);
            ButterKnife.bind(this, _inflate);
        }
    }

}
