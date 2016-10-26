package com.apanda.base.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.*

class BaseFragmentAdapter : FragmentPagerAdapter {

    internal var fragmentList: List<Fragment> = ArrayList()
    private val mTitles: List<String>? = null
    private var _title: Array<String>? = null

    constructor(fm: FragmentManager, fragmentList: List<Fragment>) : super(fm) {
        this.fragmentList = fragmentList
    }

    constructor(fm: FragmentManager, fragmentList: List<Fragment>, mTitles: List<String>) : super(fm) {
        this.fragmentList = fragmentList
        this.mTitles?.plus(mTitles)
    }

    constructor(fm: FragmentManager, _title: Array<String>) : super(fm) {
        this._title = _title
    }

    constructor(fm: FragmentManager, _fragments: List<Fragment>, _title: Array<String>) : super(fm) {
        this.fragmentList = _fragments
        this._title = _title
    }

    override fun getPageTitle(position: Int): CharSequence {
        return _title!![position]
        //        return !CollectionUtils.isNullOrEmpty(mTitles) ? mTitles.get(position) : "";
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

}
