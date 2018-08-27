package com.reigndesign.test.interfaces

import com.reigndesign.test.models.Article

interface ArticleInterface {
    interface View {
        fun showArticle(article: Article)
        fun showMessageUrlError()
    }

    interface Presenter {
        fun create(position: Int)
    }

    interface Interactor {
        fun getArticle(position: Int): Article
    }
}