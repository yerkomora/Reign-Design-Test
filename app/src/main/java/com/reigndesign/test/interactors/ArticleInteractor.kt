package com.reigndesign.test.interactors

import android.content.Context

import com.reigndesign.test.interfaces.ArticleInterface
import com.reigndesign.test.models.Article
import com.reigndesign.test.models.Articles
import com.reigndesign.test.network.MyPreferences

class ArticleInteractor(context: Context)
    : ArticleInterface.Interactor {

    private var articles = Articles()

    override fun getArticle(position: Int): Article {
        loadArticles()
        return articles.get()[position]
    }

    // My Preferences

    private val myPreferences = MyPreferences(context)

    private fun loadArticles() {
        articles = myPreferences.getArticles()
    }
}