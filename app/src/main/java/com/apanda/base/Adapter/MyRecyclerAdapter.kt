package com.apanda.base.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.util.ArrayMap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.apanda.base.R
import com.apanda.base.Utils.GlideImageLoader

import java.util.ArrayList

/**
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端
 * 作者: created by 熊凯 on 2016/10/9 15:19
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: MyRecyclerAdapter
 */

class MyRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val ITEM_TYPE_ADD = 1
    private val ITEM_TYPE_IMAGE = 2

    private val mLayoutInflater: LayoutInflater

    private var lists: List<Bitmap> = ArrayList()
    internal var addLists = ArrayMap<String, Bitmap>()
    //    private List<Integer> heights;
    private var mListener: OnItemClickListener? = null

    init {

        //  getRandomHeight(this.lists);
        mLayoutInflater = LayoutInflater.from(context)
    }

    fun setImage(lists: List<Bitmap>) {
        this.lists = lists
    }

    fun setImageAndRecord(lists: ArrayMap<String, Bitmap>) {
        this.addLists = lists
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun ItemClickListener(view: View, postion: Int)

        fun ItemLongClickListener(view: View, postion: Int)
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        this.mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        //        if (viewType == ITEM_TYPE_ADD) {
        return MyViewHolder(mLayoutInflater.inflate(R.layout.content_add_pic_recore, parent, false))
        //        } else {
        //            return new ImageViewHolder(mLayoutInflater.inflate(R.layout.item, parent, false));
        //        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        //        if (holder instanceof MyViewHolder) {
        if (addLists.keys.iterator().hasNext()) {
            val next = addLists.keys.iterator().next()
            (holder as MyViewHolder).mTv.text = next
            val bitmap = addLists[next]
            GlideImageLoader.instance.onDisplayImage(context!!, (holder as ImageViewHolder).iv as ImageView, bitmap)
        }
        //        } else if (holder instanceof ImageViewHolder) {
        //            GlideImageLoader.onDisplayImage(context, ((ImageViewHolder) holder).iv, lists.get(position));
        //        }

    }


    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
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

    override fun getItemCount(): Int {
        return lists.size + addLists.size()
    }


    internal inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mTv: TextView
        var iv: ImageView

        init {
            mTv = itemView.findViewById(R.id.tv) as TextView
            iv = itemView.findViewById(R.id.iv) as ImageView
        }
    }


    internal inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var iv: ImageView

        init {
            iv = itemView.findViewById(R.id.iv) as ImageView

        }
    }

}

