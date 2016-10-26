package com.apanda.base.API.Fragment

/**
 * Created by LiCola on  2016/04/04  23:38
 */
interface OnBoardDetailFragmentInteractionListener : OnPinsFragmentInteractionListener {

    fun onClickUserField(key: String, title: String)

    fun onHttpBoardAttentionState(userId: String, isAttention: Boolean)
}
