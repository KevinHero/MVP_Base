package com.apanda.base.Module.Gank.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apanda.base.Entity.GankBean;
import com.apanda.base.Module.Login.WebviewActivity;
import com.apanda.base.R;
import com.apanda.base.Utils.PicassoImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by apanda on 2016/10/21.
 */

public class GankAdapter extends BaseAdapter {

    private final GankBean _gankBean;


    public GankAdapter(Context context, GankBean _gankBean) {
        this._gankBean = _gankBean;

    }

    public GankAdapter(GankBean _gankBean) {
        this._gankBean = _gankBean;

    }

    @Override
    public int getCount() {
        return _gankBean.results.size();
    }

    @Override
    public GankBean.ResultsBean getItem(int _i) {
        return _gankBean.results.get(_i);
    }

    @Override
    public long getItemId(int _i) {
        return 0;
    }

    @Override
    public View getView(int _i, View _view, final ViewGroup _viewGroup) {
        ViewHolder v = null;
        if (_view == null) {
            _view = View.inflate(_viewGroup.getContext(), R.layout.item_gank, null);
            v = new ViewHolder(_view);
            _view.setTag(v);
        } else {
            v = (ViewHolder) _view.getTag();
        }

        final GankBean.ResultsBean results = getItem(_i);

        v._TvTitle.setText(results.desc);
        v._TvSource.setText(results.source);
        v._TvCato.setText(results.type);
        v._TvTime.setText(results.publishedAt);
        v._TvAuth.setText(results.who);
        if (results.images != null && results.images.size() > 0) {  v._IvImg.setVisibility(View.VISIBLE);

            new PicassoImageLoader().onDisplayImage(_viewGroup.getContext(), v._IvImg, results.images.get(0));
        }else{
            v._IvImg.setVisibility(View.GONE);
        }

        _view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                WebviewActivity.startActivity(_viewGroup.getContext(),results.desc , results.url);
            }
        });
        return _view;
    }

    static class ViewHolder {
        @Bind(R.id.tv_title)
        TextView _TvTitle;
        @Bind(R.id.tv_source)
        TextView _TvSource;
        @Bind(R.id.tv_cato)
        TextView _TvCato;
        @Bind(R.id.tv_time)
        TextView _TvTime;
        @Bind(R.id.tv_auth)
        TextView _TvAuth;
        @Bind(R.id.iv_img)
        ImageView _IvImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
