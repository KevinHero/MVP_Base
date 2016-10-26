package com.apanda.base.Widget

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView

/**
 * 深圳喂车科技有限公司源代码，版权@归喂车车所有。
 * 项目: mp_app
 * 包名: com.weicheche_b.android.costumcontrol
 * 作者: 熊凯 on 16/8/23 12:18
 * 邮箱: kai.xiong@weicheche.cn
 * 描述: ${desc}
 */
class MyGridView : GridView {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
    }

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2,
                View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }
}
