package com.reigndesign.test.models

import com.squareup.moshi.Moshi

class Articles {
    private var articles: List<Article> = listOf()

    fun set(articles: List<Article>) {
        this.articles = articles
    }

    fun get(): List<Article> {
        return this.articles
    }

    // Json

    fun toJson(): String {
        val m = Moshi.Builder().build()
        val mAdapter = m.adapter(Articles::class.java)
        return mAdapter.toJson(this)
    }

    fun fromJson(json: String) {
        val m = Moshi.Builder().build()
        val mAdapter = m.adapter(Articles::class.java)
        val articles = mAdapter.fromJson(json) as Articles

        this.articles = articles.get()
    }
}