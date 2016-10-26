package com.apanda.base.Widget.AutolinkTextview

/**
 * Created by Chatikyan on 25.09.2016-22:02.
 */

enum class AutoLinkMode private constructor(private val name: String) {

    MODE_HASHTAG("Hashtag"),
    MODE_MENTION("Mention"),
    MODE_URL("Url"),
    MODE_PHONE("Phone"),
    MODE_EMAIL("Email"),
    MODE_CUSTOM("Custom");

    override fun toString(): String {
        return name
    }
}
