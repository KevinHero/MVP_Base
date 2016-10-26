package com.apanda.base.Widget.AutolinkTextview

import android.util.Log

/**
 * Created by Chatikyan on 25.09.2016-18:57.
 */

internal object Utils {

    private fun isValidRegex(regex: String?): Boolean {
        return regex != null && !regex.isEmpty() && regex.length > 2
    }

    fun getRegexByAutoLinkMode(anAutoLinkMode: AutoLinkMode, customRegex: String): String {
        when (anAutoLinkMode) {
            AutoLinkMode.MODE_HASHTAG -> return RegexParser.HASHTAG_PATTERN
            AutoLinkMode.MODE_MENTION -> return RegexParser.MENTION_PATTERN
            AutoLinkMode.MODE_URL -> return RegexParser.URL_PATTERN
            AutoLinkMode.MODE_PHONE -> return RegexParser.PHONE_PATTERN
            AutoLinkMode.MODE_EMAIL -> return RegexParser.EMAIL_PATTERN
            AutoLinkMode.MODE_CUSTOM -> {
                if (!Utils.isValidRegex(customRegex)) {
                    Log.e(AutoLinkTextView.TAG, "Your custom regex is null, returning URL_PATTERN")
                    return RegexParser.URL_PATTERN
                } else {
                    return customRegex
                }
                return RegexParser.URL_PATTERN
            }
            else -> return RegexParser.URL_PATTERN
        }
    }

}
