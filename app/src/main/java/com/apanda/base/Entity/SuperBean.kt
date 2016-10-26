package com.apanda.base.Entity

import java.io.Serializable


class SuperBean<T> : Serializable {
    var date: String? = null
    var stories: List<StoryModel>? = null
    var top_stories: T? = null
}
