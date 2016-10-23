package com.apanda.base.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apanda.base.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端
 * 作者: created by 熊凯 on 2016/10/9 15:19
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: MyRecyclerAdapter
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mLayoutInflater;

    private Context context;
    private OnItemClickListener mListener;

    public ArticleAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }


    public interface OnItemClickListener {
        void ItemClickListener(View view, int postion);

        void ItemLongClickListener(View view, int postion);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArticleHolder(mLayoutInflater.inflate(R.layout.item_recommend_article, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (mListener != null) {//如果设置了监听那么它就不为空，然后回调相应的方法
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                    mListener.ItemClickListener(holder.itemView, pos);//把事件交给我们实现的接口那里处理
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                    mListener.ItemLongClickListener(holder.itemView, pos);//把事件交给我们实现的接口那里处理
                    return true;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return 0;
    }


    static class ArticleHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_back_img)
        ImageView _IvBackImg;
        @Bind(R.id.iv_header)
        ImageView _IvHeader;
        @Bind(R.id.tv_nickname)
        TextView _TvNickname;
        @Bind(R.id.tv_title)
        TextView _TvTitle;
        @Bind(R.id.tv_intro)
        TextView _TvIntro;
        @Bind(R.id.tv_collocts)
        TextView _TvCollocts;
        @Bind(R.id.tv_shares)
        TextView _TvShares;

        ArticleHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

