package com.apanda.base.Widget.AutolinkTextview

import android.text.Layout
import android.text.Selection
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.view.MotionEvent
import android.widget.TextView

/**
 * Created by Chatikyan on 26.09.2016-19:09.
 */

internal class LinkTouchMovementMethod : LinkMovementMethod() {

    private var pressedSpan: TouchableSpan? = null

    override fun onTouchEvent(textView: TextView, spannable: Spannable, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            pressedSpan = getPressedSpan(textView, spannable, event)
            if (pressedSpan != null) {
                pressedSpan!!.setPressed(true)
                Selection.setSelection(spannable, spannable.getSpanStart(pressedSpan),
                        spannable.getSpanEnd(pressedSpan))
            }
        } else if (event.action == MotionEvent.ACTION_MOVE) {
            val touchedSpan = getPressedSpan(textView, spannable, event)
            if (pressedSpan != null && touchedSpan !== pressedSpan) {
                pressedSpan!!.setPressed(false)
                pressedSpan = null
                Selection.removeSelection(spannable)
            }
        } else {
            if (pressedSpan != null) {
                pressedSpan!!.setPressed(false)
                super.onTouchEvent(textView, spannable, event)
            }
            pressedSpan = null
            Selection.removeSelection(spannable)
        }
        return true
    }

    private fun getPressedSpan(textView: TextView, spannable: Spannable, event: MotionEvent): TouchableSpan {

        var x = event.x.toInt()
        var y = event.y.toInt()

        x -= textView.totalPaddingLeft
        y -= textView.totalPaddingTop

        x += textView.scrollX
        y += textView.scrollY

        val layout = textView.layout
        val line = layout.getLineForVertical(y)
        val off = layout.getOffsetForHorizontal(line, x.toFloat())

        val link = spannable.getSpans(off, off, TouchableSpan::class.java)
        var touchedSpan: TouchableSpan? = null
        if (link.size > 0) {
            touchedSpan = link[0]
        }
        return touchedSpan
    }
}
