package com.reigndesign.test

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

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

    // Articles

    fun setArticles(articles: Articles) {
        val json = articles.toJson()

        if (sharedPreferences != null) {
            val editor = sharedPreferences?.edit()
            editor?.putString(ARTICLES, json)
            editor?.apply()
        }
    }

    fun getArticles(): Articles {
        val articles = Articles()

        if (sharedPreferences != null) {
            val json = sharedPreferences?.getString(ARTICLES, "")
            articles.fromJson(json)
        }

        return articles
    }
}