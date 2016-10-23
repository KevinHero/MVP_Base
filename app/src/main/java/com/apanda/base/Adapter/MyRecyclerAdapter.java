package com.apanda.base.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apanda.base.R;
import com.apanda.base.Utils.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端
 * 作者: created by 熊凯 on 2016/10/9 15:19
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: MyRecyclerAdapter
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int ITEM_TYPE_ADD = 1;
    private final int ITEM_TYPE_IMAGE = 2;

    private final LayoutInflater mLayoutInflater;

    private List<Bitmap> lists = new ArrayList<>();
    ArrayMap<String, Bitmap> addLists = new ArrayMap<>();
    private Context context;
    //    private List<Integer> heights;
    private OnItemClickListener mListener;

    public MyRecyclerAdapter(Context context) {
        this.context = context;

        //  getRandomHeight(this.lists);
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setImage(List<Bitmap> lists) {
        this.lists = lists;
    }

    public void setImageAndRecord(ArrayMap<String, Bitmap> lists) {
        this.addLists = lists;
        notifyDataSetChanged();
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


//        if (viewType == ITEM_TYPE_ADD) {
        return new MyViewHolder(mLayoutInflater.inflate(R.layout.content_add_pic_recore, parent, false));
//        } else {
//            return new ImageViewHolder(mLayoutInflater.inflate(R.layout.item, parent, false));
//        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

//        if (holder instanceof MyViewHolder) {
        if (addLists.keySet().iterator().hasNext()) {
            String next = addLists.keySet().iterator().next();
            ((MyViewHolder) holder).mTv.setText(next);
            Bitmap bitmap = addLists.get(next);
              GlideImageLoader.getInstance().onDisplayImage(context, ((ImageViewHolder) holder).iv, bitmap);
        }
//        } else if (holder instanceof ImageViewHolder) {
//            GlideImageLoader.onDisplayImage(context, ((ImageViewHolder) holder).iv, lists.get(position));
//        }

    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

//    @Override
//    public void onBindViewHolder(final MyViewHolder holder, int position) {
//        //ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();//得到item的LayoutParams布局参数
//        //params.height = heights.get(position);//把随机的高度赋予item布局
//        //holder.itemView.setLayoutParams(params);//把params设置给item布局
//
//        //holder.mTv.setText(lists.get(position));//为控件绑定数据
//        if (mListener != null) {//如果设置了监听那么它就不为空，然后回调相应的方法
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
//                    mListener.ItemClickListener(holder.itemView, pos);//把事件交给我们实现的接口那里处理
//                }
//            });
//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
//                    mListener.ItemLongClickListener(holder.itemView, pos);//把事件交给我们实现的接口那里处理
//                    return true;
//                }
//            });
//        }
    //}

    @Override
    public int getItemCount() {
        return lists.size() + addLists.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.tv);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }


    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;

        ImageViewHolder(View view) {
            super(view);
            iv = (ImageView) itemView.findViewById(R.id.iv);

        }
    }

}

