package com.reigndesign.test.interactors

import android.content.Context

import com.reigndesign.test.interfaces.ArticlesInterface
import com.reigndesign.test.models.Articles
import com.reigndesign.test.models.Result
import com.reigndesign.test.network.MyPreferences
import com.reigndesign.test.network.RetrofitClient
import com.reigndesign.test.network.RetrofitService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticlesInteractor(context: Context)
    : ArticlesInterface.Interactor {

    private var articles = Articles()

    override fun getArticle(onArticles: ArticlesInterface.Interactor.OnArticles) {

        val products = retrofitService?.getProducts()

        products?.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                if (response.isSuccessful) {
                    val result = response.body() as Result

                    articles.set(result.hits)
                    articles.removeDeletes()

                    onArticles.success(articles.get())
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                onArticles.failed(articles.get())
            }
        })
    }

    override fun removeArticle(position: Int
                               , onRemoveArticle: ArticlesInterface.Interactor.OnRemoveArticle) {

        articles.removeArticle(position)
        onRemoveArticle.success()
    }

    // My Preferences

    private val myPreferences = MyPreferences(context)

    fun loadArticles() {
        articles = myPreferences.getArticles()
    }

    fun saveArticles() {
        myPreferences.setArticles(articles)
    }

    // Retrofit

    private val retrofitService: RetrofitService? =
            RetrofitClient.instance?.create(RetrofitService::class.java)
}