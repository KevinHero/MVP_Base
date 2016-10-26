package com.apanda.base.API.Fragment

import android.view.View

/**
 * Created by LiCola on  2016/04/04  23:31
 * 所有有pins对象列表的 共用接口
 */
interface OnBoardFragmentInteractionListener<T> {
    fun onClickBoardItemImage(bean: T, view: View)

    //    void onClickBoardItemOperate(T bean, View view);
}
