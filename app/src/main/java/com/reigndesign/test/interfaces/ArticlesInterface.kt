package com.reigndesign.test.interfaces

import com.reigndesign.test.models.Article

interface ArticlesInterface {

    interface View {
        fun showArticles(articles: ArrayList<Article>)
        fun showMessageOffLine()

        fun removeArticle(position: Int)
        fun showRemoveFailed()

        fun goArticle(position: Int)
        fun showMessageNotUrl() {}
    }

    interface Presenter {
        fun create()
        fun resume()
        fun pause()

        fun refreshArticles()
        fun clickArticle(article: Article)
        fun swipeArticle(position: Int)
    }

    interface Interactor {

        interface OnArticles {
            fun success(articles: List<Article>)
            fun failed(articles: List<Article>)
        }

        fun getArticle(onArticles: OnArticles)

        interface OnRemoveArticle {
            fun success()
            fun failed()
        }

        fun removeArticle(position: Int, onRemoveArticle: OnRemoveArticle)
    }
}