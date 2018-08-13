package com.reigndesign.test.activities

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.Toast

import com.reigndesign.test.MyApplication
import com.reigndesign.test.R
import com.reigndesign.test.adapters.ArticleAdapter
import com.reigndesign.test.adapters.MySimpleCallback
import com.reigndesign.test.models.Article
import com.reigndesign.test.models.Articles
import com.reigndesign.test.models.Result
import com.reigndesign.test.network.RetrofitClient
import com.reigndesign.test.network.RetrofitService

import kotlinx.android.synthetic.main.activity_main.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ParentActivity() {

    private lateinit var myApplication: MyApplication
    private var retrofitService: RetrofitService? = null
    private lateinit var articleAdapter: ArticleAdapter

    private var articles: Articles = Articles()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // MyApplication

        myApplication = application as MyApplication

        // RecyclerView

        rvArticles.setHasFixedSize(true)

        articleAdapter = ArticleAdapter()

        articleAdapter.onListener = object : ArticleAdapter.OnListener {
            override fun onClick(article: Article) {
                if (article.urlValid())
                    goArticle(article)
                else {
                    Toast.makeText(this@MainActivity, R.string.url_not, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Swipe

        val itemTouchHelper = ItemTouchHelper(
                object : MySimpleCallback(0, ItemTouchHelper.LEFT) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, p1: Int) {
                        val position = viewHolder.adapterPosition

                        articles.removeArticle(position)
                        articleAdapter.removeArticle(position)
                    }
                })

        itemTouchHelper.attachToRecyclerView(rvArticles)

        rvArticles.adapter = articleAdapter

        // Retrofit

        retrofitService = RetrofitClient.instance?.create(RetrofitService::class.java)

        setArticles()

        // SwipeRefreshLayout

        srlProducts.setOnRefreshListener { setArticles() }
    }

    private fun setArticles() {
        val products = retrofitService?.getProducts()
        products?.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                if (response.isSuccessful) {
                    val result = response.body() as Result

                    articles.set(result.hits)
                    articles.removeDeletes()

                    articleAdapter.articles = ArrayList(articles.get())
                    srlProducts.isRefreshing = false
                    pbArticles.hide()
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                Toast.makeText(this@MainActivity, R.string.offline, Toast.LENGTH_SHORT).show()
                articleAdapter.articles = ArrayList(articles.get())
                srlProducts.isRefreshing = false
                pbArticles.hide()
            }
        })
    }

    override fun onPause() {
        super.onPause()
        myApplication.setArticles(articles)
    }

    override fun onResume() {
        super.onResume()
        articles = myApplication.getArticles()
    }
}