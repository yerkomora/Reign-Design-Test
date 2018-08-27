package com.reigndesign.test.network

import android.content.Context
import android.content.SharedPreferences

import com.reigndesign.test.models.Articles

class MyPreferences(context: Context) {

    companion object {
        const val DATA: String = "data"
        const val ARTICLES: String = "articles"
    }

    private var sharedPreferences: SharedPreferences? = context.getSharedPreferences(DATA
            , Context.MODE_PRIVATE)

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