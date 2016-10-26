package com.apanda.base.Widget.commonwidget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.apanda.base.R
import com.apanda.base.Utils.DisplayUtil

class NormalTitleBar : RelativeLayout {

    private val ivRight: ImageView
    private val ivBack: TextView
    private val tvTitle: TextView
    private val tvRight: TextView
    private val rlCommonTitle: RelativeLayout
    private var context: Context? = null

    constructor(context: Context) : super(context, null) {
        this.context = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        View.inflate(context, R.layout.bar_normal, this)
        ivBack = findViewById(R.id.tv_back) as TextView
        tvTitle = findViewById(R.id.tv_title) as TextView
        tvRight = findViewById(R.id.tv_right) as TextView
        ivRight = findViewById(R.id.image_right) as ImageView
        rlCommonTitle = findViewById(R.id.common_title) as RelativeLayout
        //setHeaderHeight();
    }

    fun setHeaderHeight() {
        rlCommonTitle.setPadding(0, DisplayUtil.getStatusBarHeight(context), 0, 0)
        rlCommonTitle.requestLayout()
    }

    /**
     * 管理返回按钮
     */
    fun setBackVisibility(visible: Boolean) {
        if (visible) {
            ivBack.visibility = View.VISIBLE
        } else {
            ivBack.visibility = View.GONE
        }
    }

    /**
     * 设置标题栏左侧字符串
     * @param visiable
     */
    fun setTvLeftVisiable(visiable: Boolean) {
        if (visiable) {
            ivBack.visibility = View.VISIBLE
        } else {
            ivBack.visibility = View.GONE
        }
    }

    /**
     * 设置标题栏左侧字符串
     * @param tvLeftText
     */
    fun setTvLeft(tvLeftText: String) {
        ivBack.text = tvLeftText
    }


    /**
     * 管理标题
     */
    fun setTitleVisibility(visible: Boolean) {
        if (visible) {
            tvTitle.visibility = View.VISIBLE
        } else {
            tvTitle.visibility = View.GONE
        }
    }

    fun setTitleText(string: String) {
        tvTitle.text = string
    }

    fun setTitleText(string: Int) {
        tvTitle.setText(string)
    }

    fun setTitleColor(color: Int) {
        tvTitle.setTextColor(color)
    }

    /**
     * 右图标
     */
    fun setRightImagVisibility(visible: Boolean) {
        ivRight.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setRightImagSrc(id: Int) {
        ivRight.visibility = View.VISIBLE
        ivRight.setImageResource(id)
    }

    /**
     * 获取右按钮
     * @return
     */
    val rightImage: View
        get() = ivRight

    /**
     * 左图标

     * @param id
     */
    fun setLeftImagSrc(id: Int) {
        ivBack.setCompoundDrawables(resources.getDrawable(id), null, null, null)
    }

    /**
     * 左文字

     * @param str
     */
    fun setLeftTitle(str: String) {
        ivBack.text = str
    }

    /**
     * 右标题
     */
    fun setRightTitleVisibility(visible: Boolean) {
        tvRight.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setRightTitle(text: String) {
        tvRight.text = text
    }

    /*
     * 点击事件
     */
    fun setOnBackListener(listener: View.OnClickListener) {
        ivBack.setOnClickListener(listener)
    }

    fun setOnRightImagListener(listener: View.OnClickListener) {
        ivRight.setOnClickListener(listener)
    }

    fun setOnRightTextListener(listener: View.OnClickListener) {
        tvRight.setOnClickListener(listener)
    }

    /**
     * 标题背景颜色

     * @param color
     */
    fun setBackGroundColor(color: Int) {
        rlCommonTitle.setBackgroundColor(color)
    }

    val backGroundDrawable: Drawable
        get() = rlCommonTitle.background

}
