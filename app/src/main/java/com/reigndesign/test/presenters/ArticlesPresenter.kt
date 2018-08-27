package com.reigndesign.test.presenters

import android.content.Context

import com.reigndesign.test.interactors.ArticlesInteractor
import com.reigndesign.test.interfaces.ArticlesInterface
import com.reigndesign.test.models.Article

class ArticlesPresenter(context: Context, val view: ArticlesInterface.View
) : ArticlesInterface.Presenter {

    private val interactor = ArticlesInteractor(context)

    private fun showArticles() {
        interactor.getArticle(object : ArticlesInterface.Interactor.OnArticles {
            override fun success(articles: List<Article>) {
                view.showArticles(ArrayList(articles))
            }

            override fun failed(articles: List<Article>) {
                view.showMessageOffLine()
                view.showArticles(ArrayList(articles))
            }
        })
    }

    // ArticlesInterface.Present

    override fun create() {
        showArticles()
    }

    override fun resume() {
        interactor.loadArticles()
    }

    override fun pause() {
        interactor.saveArticles()
    }

    override fun refreshArticles() {
        showArticles()
    }

    override fun clickArticle(article: Article) {
        if (article.urlValid()) {
            view.goArticle(article.position)
        } else {
            view.showMessageNotUrl()
        }
    }

    override fun swipeArticle(position: Int) {
        interactor.removeArticle(position, object : ArticlesInterface.Interactor.OnRemoveArticle {
            override fun success() {
                view.removeArticle(position)
            }

            override fun failed() {
                view.showRemoveFailed()
            }
        })
    }
}