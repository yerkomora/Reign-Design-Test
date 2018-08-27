package com.reigndesign.test.presenters

import android.content.Context

import com.reigndesign.test.interactors.ArticleInteractor
import com.reigndesign.test.interfaces.ArticleInterface

class ArticlePresenter(context: Context, val view: ArticleInterface.View)
    : ArticleInterface.Presenter {

    private val interactor = ArticleInteractor(context)

    override fun create(position: Int) {
        val article = interactor.getArticle(position)

        if (article.urlValid())
            view.showArticle(article)
        else
            view.showMessageUrlError()
    }
}