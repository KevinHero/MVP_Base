package com.apanda.base.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.apanda.base.R

import butterknife.Bind
import butterknife.ButterKnife

/**
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: onlyGo客户端
 * 作者: created by 熊凯 on 2016/10/9 15:19
 * 邮箱: xiongkai@zhizaizi.com
 * 描述: MyRecyclerAdapter
 */

class ArticleAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mLayoutInflater: LayoutInflater
    private var mListener: OnItemClickListener? = null

    init {
        mLayoutInflater = LayoutInflater.from(context)
    }


    interface OnItemClickListener {
        fun ItemClickListener(view: View, postion: Int)

        fun ItemLongClickListener(view: View, postion: Int)
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        this.mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArticleHolder(mLayoutInflater.inflate(R.layout.item_recommend_article, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (mListener != null) {//如果设置了监听那么它就不为空，然后回调相应的方法
            holder.itemView.setOnClickListener {
                val pos = holder.layoutPosition//得到当前点击item的位置pos
                mListener!!.ItemClickListener(holder.itemView, pos)//把事件交给我们实现的接口那里处理
            }
            holder.itemView.setOnLongClickListener {
                val pos = holder.layoutPosition//得到当前点击item的位置pos
                mListener!!.ItemLongClickListener(holder.itemView, pos)//把事件交给我们实现的接口那里处理
                true
            }
        }
    }


    override fun getItemCount(): Int {
        return 0
    }


    internal class ArticleHolder(view: View) : RecyclerView.ViewHolder(view) {
        @Bind(R.id.iv_back_img)
        var _IvBackImg: ImageView? = null
        @Bind(R.id.iv_header)
        var _IvHeader: ImageView? = null
        @Bind(R.id.tv_nickname)
        var _TvNickname: TextView? = null
        @Bind(R.id.tv_title)
        var _TvTitle: TextView? = null
        @Bind(R.id.tv_intro)
        var _TvIntro: TextView? = null
        @Bind(R.id.tv_collocts)
        var _TvCollocts: TextView? = null
        @Bind(R.id.tv_shares)
        var _TvShares: TextView? = null

        init {
            ButterKnife.bind(this, view)
        }
    }
}

