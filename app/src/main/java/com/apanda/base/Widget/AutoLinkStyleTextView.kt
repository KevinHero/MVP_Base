package com.apanda.base.Widget

/**
 * Created by Android on 2016/10/13.
 */

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

import com.apanda.base.R


class AutoLinkStyleTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet = null, defStyleAttr: Int = 0) : TextView(context, attrs, defStyleAttr) {

    private var mClickCallBack: ClickCallBack? = null

    init {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoLinkStyleTextView, defStyleAttr, 0)
        DEFAULT_TEXT_VALUE = typedArray.getString(R.styleable.AutoLinkStyleTextView_AutoLinkStyleTextView_text_value)
        DEFAULT_COLOR = typedArray.getColor(R.styleable.AutoLinkStyleTextView_AutoLinkStyleTextView_default_color, DEFAULT_COLOR)
        HAS_UNDER_LINE = typedArray.getBoolean(R.styleable.AutoLinkStyleTextView_AutoLinkStyleTextView_has_under_line, HAS_UNDER_LINE)
        addStyle()
    }

    private fun addStyle() {
        if (!TextUtils.isEmpty(DEFAULT_TEXT_VALUE) && DEFAULT_TEXT_VALUE!!.contains(",")) {
            val values = DEFAULT_TEXT_VALUE!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val spannableString = SpannableString(text.toString().trim { it <= ' ' })
            for (i in values.indices) {
                val position = i
                spannableString.setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        if (mClickCallBack != null) mClickCallBack!!.onClick(position)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = DEFAULT_COLOR
                        ds.isUnderlineText = HAS_UNDER_LINE
                    }
                }, text.toString().trim { it <= ' ' }.indexOf(values[i]), text.toString().trim { it <= ' ' }.indexOf(values[i]) + values[i].length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
        }
    }


    fun setOnClickCallBack(clickCallBack: (Any) -> Unit) {
        this.mClickCallBack = clickCallBack
    }

    interface ClickCallBack {
        fun onClick(position: Int)
    }

    companion object {

        private var DEFAULT_TEXT_VALUE: String? = null
        private var DEFAULT_COLOR = Color.parseColor("#f23218")
        private var HAS_UNDER_LINE = true
    }
}