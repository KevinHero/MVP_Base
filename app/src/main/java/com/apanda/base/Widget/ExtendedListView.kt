package com.apanda.base.Widget

/**
 * Created by Android on 2016/10/11.
 */


import android.content.Context
import android.util.AttributeSet
import android.widget.AbsListView
import android.widget.ListAdapter
import android.widget.ListView

/**
 * Add a method to the basic list view.
 * setOnEndOfListListener();
 */
class ExtendedListView : ListView {

    private var onEndOfListListener: OnEndOfListListener<*>? = null

    private var hasWarned = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
            }

            override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                if (hasWarned
                        || view.lastVisiblePosition != totalItemCount - 1
                        || onEndOfListListener == null)
                    return

                hasWarned = true
                val lastItem = view.getItemAtPosition(totalItemCount - 1)
                if (lastItem != null || totalItemCount == 0)
                    onEndOfListListener!!.onEndOfList(lastItem)
            }
        })
    }

    override fun handleDataChanged() {
        super.handleDataChanged()
        hasWarned = false
    }

    override fun setAdapter(adapter: ListAdapter) {
        super.setAdapter(adapter)
        hasWarned = false
    }

    fun setOnEndOfListListener(onEndOfListListener: OnEndOfListListener<*>) {
        this.onEndOfListListener = onEndOfListListener
    }

    interface OnEndOfListListener<T> {
        fun onEndOfList(lastItem: T)
    }
}