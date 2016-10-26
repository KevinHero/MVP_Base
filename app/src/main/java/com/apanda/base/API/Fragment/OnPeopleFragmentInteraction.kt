package com.apanda.base.API.Fragment

import android.view.View

/**
 * Created by LiCola on  2016/04/05  22:46
 */
interface OnPeopleFragmentInteraction<T> {
    fun onClickItemUser(bean: T, view: View)
}
