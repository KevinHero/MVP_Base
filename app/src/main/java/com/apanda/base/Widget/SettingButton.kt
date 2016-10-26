package com.apanda.base.Widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.apanda.base.R


/**
 * Created by Android on 2016/10/8.
 */

class SettingButton : FrameLayout, View.OnClickListener {

    private val _titleText: TextView
    private val _subtitle: TextView
    val imageView: ImageView
    private val _button: LinearLayout

    constructor(context: Context) : super(context) {
        setOnClickListener(this)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setOnClickListener(this)
        LayoutInflater.from(context).inflate(R.layout.setting_button, this)
        _titleText = findViewById(R.id.title) as TextView
        _subtitle = findViewById(R.id.subtitle) as TextView
        imageView = findViewById(R.id.logo) as ImageView
        _button = findViewById(R.id.button) as LinearLayout
        _button.isClickable = true
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }


    override fun onClick(_view: View) {

    }

    fun setTitleText(text: String) {
        _titleText.text = text
    }

    fun imageGone() {
        imageView.visibility = View.GONE
    }

    fun imageVisiablity() {
        imageView.visibility = View.VISIBLE
    }

    fun subtitleGone() {
        _subtitle.visibility = View.GONE
    }

    fun subtitleVisiablity() {
        _subtitle.visibility = View.VISIBLE
    }


    fun setSubtitleText(text: String) {
        _subtitle.text = text
    }

    fun setButtonListener(l: View.OnClickListener) {

        _button.setOnClickListener(l)
    }

}
