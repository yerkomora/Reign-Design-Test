package com.reigndesign.test

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.reigndesign.test.models.Article
import com.reigndesign.test.models.Articles

class MyApplication : Application() {

    companion object {
        const val DATA: String = "data"
        const val ARTICLES: String = "articles"
    }

    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences(DATA, Context.MODE_PRIVATE)
    }

    private var articles: String = ""

    fun setArticles(articleList: List<Article>) {
        val articles = Articles()
        articles.set(articleList)
        val json = articles.toJson()

        val editor = sharedPreferences?.edit()
        editor?.putString(ARTICLES, json)
        editor?.apply()
    }

    fun getArticles(): List<Article> {
        val articles = Articles()

        val json = sharedPreferences?.getString(ARTICLES, "")
        if (json != null)
            articles.fromJson(json)

        return articles.get()
    }
}