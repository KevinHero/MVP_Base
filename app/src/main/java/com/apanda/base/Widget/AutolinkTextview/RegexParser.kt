package com.apanda.base.Widget.AutolinkTextview

import android.util.Patterns

/**
 * Created by Chatikyan on 25.09.2016-19:23.
 */

internal object RegexParser {

    val PHONE_PATTERN = Patterns.PHONE.pattern()
    val EMAIL_PATTERN = Patterns.EMAIL_ADDRESS.pattern()
    val HASHTAG_PATTERN = "(?:^|\\s|$)#[\\p{L}0-9_]*"
    val MENTION_PATTERN = "(?:^|\\s|$|[.])@[\\p{L}0-9_]*"
    val URL_PATTERN = "(^|[\\s.:;?\\-\\]<\\(])" +
            "((https?://|www\\.|pic\\.)[-\\w;/?:@&=+$\\|\\_.!~*\\|'()\\[\\]%#,☺]+[\\w/#](\\(\\))?)" +
            "(?=$|[\\s',\\|\\(\\).:;?\\-\\[\\]>\\)])"
}
