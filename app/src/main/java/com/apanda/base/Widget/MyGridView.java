package com.apanda.base.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 深圳喂车科技有限公司源代码，版权@归喂车车所有。
 * 项目: mp_app
 * 包名: com.weicheche_b.android.costumcontrol
 * 作者: 熊凯 on 16/8/23 12:18
 * 邮箱: kai.xiong@weicheche.cn
 * 描述: ${desc}
 */
public class MyGridView extends GridView {
    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
