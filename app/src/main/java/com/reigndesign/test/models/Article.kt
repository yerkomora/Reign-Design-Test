package com.reigndesign.test.models

import com.squareup.moshi.Moshi

class Article {
    companion object {
        const val POSITION: String = "position"
    }

    @Transient
    var position: Int = 0

    var objectID: Int? = null
    var title: String? = null
    var story_title: String? = null
    var author: String = ""
    var created_at: String = ""
    var url: String? = null

    fun urlValid(): Boolean {
        return !(url == null || url?.isEmpty()!!)
    }

    fun getStoryOrTitle(): String {
        return if (story_title == null || story_title?.isEmpty()!!) {
            if (title == null || title?.isEmpty()!!)
                ""
            else
                title.toString()
        } else story_title.toString()
    }

    // JSON

    fun toJson(): String {
        val m = Moshi.Builder().build()
        val mAdapter = m.adapter(Article::class.java)
        return mAdapter.toJson(this)
    }

    fun fromJson(json: String) {
        val m = Moshi.Builder().build()
        val mAdapter = m.adapter(Article::class.java)
        val article = mAdapter.fromJson(json) as Article

        this.title = article.title
        this.story_title = article.story_title
        this.author = article.author
        this.created_at = article.created_at
        this.url = article.url
    }
}