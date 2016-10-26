package com.apanda.base.Widget.AutolinkTextview

import android.content.Context
import android.graphics.Color
import android.support.annotation.ColorInt
import android.text.Spannable
import android.text.SpannableString
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

import java.util.LinkedList
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by Chatikyan on 25.09.2016-18:53.
 */

class AutoLinkTextView : TextView {

    private var autoLinkOnClickListener: AutoLinkOnClickListener? = null

    private var autoLinkModes: Array<AutoLinkMode>? = null

    private var customRegex: String? = null

    private var isUnderLineEnabled = false

    private var mentionModeColor = DEFAULT_COLOR
    private var hashtagModeColor = DEFAULT_COLOR
    private var urlModeColor = DEFAULT_COLOR
    private var phoneModeColor = DEFAULT_COLOR
    private var emailModeColor = DEFAULT_COLOR
    private var customModeColor = DEFAULT_COLOR
    private var defaultSelectedColor = Color.LTGRAY

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    override fun setHighlightColor(color: Int) {
        super.setHighlightColor(Color.TRANSPARENT)
    }

    fun setAutoLinkText(text: String) {
        val spannableString = makeSpannableString(text)
        setText(spannableString)
        movementMethod = LinkTouchMovementMethod()
    }

    private fun makeSpannableString(text: String): SpannableString {

        val spannableString = SpannableString(text)

        val autoLinkItems = matchedRanges(text)

        for (autoLinkItem in autoLinkItems) {
            val currentColor = getColorByMode(autoLinkItem.autoLinkMode)

            val clickableSpan = object : TouchableSpan(currentColor, defaultSelectedColor, isUnderLineEnabled) {
                override fun onClick(widget: View) {
                    if (autoLinkOnClickListener != null)
                        autoLinkOnClickListener!!.onAutoLinkTextClick(
                                autoLinkItem.autoLinkMode,
                                autoLinkItem.matchedText)
                }
            }

            spannableString.setSpan(
                    clickableSpan,
                    autoLinkItem.startPoint,
                    autoLinkItem.endPoint,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return spannableString
    }

    private fun matchedRanges(text: String): List<AutoLinkItem> {

        val autoLinkItems = LinkedList<AutoLinkItem>()

        if (autoLinkModes == null) {
            throw NullPointerException("Please add at least one mode")
        }

        for (anAutoLinkMode in autoLinkModes!!) {
            val regex = Utils.getRegexByAutoLinkMode(anAutoLinkMode, customRegex)
            val pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(text)

            if (anAutoLinkMode == AutoLinkMode.MODE_PHONE) {
                while (matcher.find()) {
                    if (matcher.group().length > MIN_PHONE_NUMBER_LENGTH)
                        autoLinkItems.add(AutoLinkItem(
                                matcher.start(),
                                matcher.end(),
                                matcher.group(),
                                anAutoLinkMode))
                }
            } else {
                while (matcher.find()) {
                    autoLinkItems.add(AutoLinkItem(
                            matcher.start(),
                            matcher.end(),
                            matcher.group(),
                            anAutoLinkMode))
                }
            }
        }

        return autoLinkItems
    }


    private fun getColorByMode(autoLinkMode: AutoLinkMode): Int {
        when (autoLinkMode) {
            AutoLinkMode.MODE_HASHTAG -> return hashtagModeColor
            AutoLinkMode.MODE_MENTION -> return mentionModeColor
            AutoLinkMode.MODE_URL -> return urlModeColor
            AutoLinkMode.MODE_PHONE -> return phoneModeColor
            AutoLinkMode.MODE_EMAIL -> return emailModeColor
            AutoLinkMode.MODE_CUSTOM -> return customModeColor
            else -> return DEFAULT_COLOR
        }
    }

    fun setMentionModeColor(@ColorInt mentionModeColor: Int) {
        this.mentionModeColor = mentionModeColor
    }

    fun setHashtagModeColor(@ColorInt hashtagModeColor: Int) {
        this.hashtagModeColor = hashtagModeColor
    }

    fun setUrlModeColor(@ColorInt urlModeColor: Int) {
        this.urlModeColor = urlModeColor
    }

    fun setPhoneModeColor(@ColorInt phoneModeColor: Int) {
        this.phoneModeColor = phoneModeColor
    }

    fun setEmailModeColor(@ColorInt emailModeColor: Int) {
        this.emailModeColor = emailModeColor
    }

    fun setCustomModeColor(@ColorInt customModeColor: Int) {
        this.customModeColor = customModeColor
    }

    fun setSelectedStateColor(@ColorInt defaultSelectedColor: Int) {
        this.defaultSelectedColor = defaultSelectedColor
    }

    fun addAutoLinkMode(vararg autoLinkModes: AutoLinkMode) {
        this.autoLinkModes = autoLinkModes
    }

    fun setCustomRegex(regex: String) {
        this.customRegex = regex
    }

    fun setAutoLinkOnClickListener(autoLinkOnClickListener: AutoLinkOnClickListener) {
        this.autoLinkOnClickListener = autoLinkOnClickListener
    }

    fun setUnderLineEnabled(underLineEnabled: Boolean) {
        isUnderLineEnabled = underLineEnabled
    }

    companion object {

        internal val TAG = AutoLinkTextView::class.java.simpleName

        private val MIN_PHONE_NUMBER_LENGTH = 8

        private val DEFAULT_COLOR = Color.RED
    }
}
