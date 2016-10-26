/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.apanda.base.Utils

import android.app.Activity
import android.support.design.widget.TabLayout
import android.view.View
import android.view.ViewGroup

import com.apanda.base.base.BaseApplication


/**
 * @author 咖枯
 * *
 * @version 1.0 2016/5/31
 */
object TabHostUtils {

    fun dynamicSetTabLayoutMode(tabLayout: TabLayout) {
        val tabWidth = calculateTabWidth(tabLayout)
        val screenWidth = screenWith

        if (tabWidth <= screenWidth) {
            tabLayout.tabMode = TabLayout.MODE_FIXED
        } else {
            tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        }
    }

    private fun calculateTabWidth(tabLayout: TabLayout): Int {
        var tabWidth = 0
        for (i in 0..tabLayout.childCount - 1) {
            val view = tabLayout.getChildAt(i)
            view.measure(0, 0) // 通知父view测量，以便于能够保证获取到宽高
            tabWidth += view.measuredWidth
        }
        return tabWidth
    }

    val screenWith: Int
        get() = BaseApplication.instance.getResources().getDisplayMetrics().widthPixels

    fun getRootView(context: Activity): View {
        return (context.findViewById(android.R.id.content) as ViewGroup).getChildAt(0)
    }
}
