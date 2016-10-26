package com.apanda.base.Widget.AutolinkTextview

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan

/**
 * Created by Chatikyan on 26.09.2016-19:10.
 */

internal abstract class TouchableSpan(private val normalTextColor: Int, private val pressedTextColor: Int, private val isUnderLineEnabled: Boolean) : ClickableSpan() {

    private var isPressed: Boolean = false

    fun setPressed(isSelected: Boolean) {
        isPressed = isSelected
    }

    override fun updateDrawState(textPaint: TextPaint) {
        super.updateDrawState(textPaint)
        textPaint.color = if (isPressed) pressedTextColor else normalTextColor
        textPaint.bgColor = Color.TRANSPARENT
        textPaint.isUnderlineText = isUnderLineEnabled
    }
}