package com.apanda.base.API.Fragment

/**
 * Created by LiCola on  2016/04/04  23:38
 */
interface OnImageDetailFragmentInteractionListener : OnPinsFragmentInteractionListener {
    fun onClickBoardField(key: String, title: String)

    fun onClickUserField(key: String, title: String)

    fun onClickImageLink(link: String)
}
