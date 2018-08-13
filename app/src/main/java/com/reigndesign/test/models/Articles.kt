package com.reigndesign.test.models

import com.squareup.moshi.Moshi

class Articles {

    // Articles

    private var articles: List<Article> = listOf()

    fun set(articles: List<Article>) {
        this.articles = articles
    }

    fun get(): List<Article> {
        return this.articles
    }

    fun removeArticle(position: Int) {
        val objectId = articles[position].objectID
        addDelete(objectId)

        val articlesArrayList = ArrayList(articles)
        articlesArrayList.removeAt(position)
        articles = articlesArrayList.toList()
    }

    // Json

    fun toJson(): String {
        val m = Moshi.Builder().build()
        val mAdapter = m.adapter(Articles::class.java)
        return mAdapter.toJson(this)
    }

    fun fromJson(json: String?) {
        if (json != null && !json.isEmpty()) {
            val m = Moshi.Builder().build()
            val mAdapter = m.adapter(Articles::class.java)
            val articles = mAdapter.fromJson(json) as Articles

            this.articles = articles.get()
        }
    }

    // Deletes

    private var deletes: List<Int> = ArrayList()

    private fun addDelete(i: Int?) {
        if (i != null && !findDelete(i)) {
            val deletesArrayList = ArrayList(deletes)
            deletesArrayList.add(i)
            deletes = deletesArrayList.toList()
        }
    }

    private fun findDelete(i: Int?): Boolean {
        return if (i == null)
            false
        else
            deletes.contains(i)
    }

    fun removeDeletes() {
        val articlesArrayList = ArrayList(articles)

        for (i in (articlesArrayList.size - 1) downTo 0) {
            val article = articlesArrayList[i]
            if (findDelete(article.objectID))
                articlesArrayList.removeAt(i)
        }

        articles = articlesArrayList.toList()
    }
}