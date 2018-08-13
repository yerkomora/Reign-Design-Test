package com.reigndesign.test.activities

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.reigndesign.test.MyApplication

import com.reigndesign.test.R
import com.reigndesign.test.adapters.ArticleAdapter
import com.reigndesign.test.models.Article
import com.reigndesign.test.models.Result
import com.reigndesign.test.network.RetrofitClient
import com.reigndesign.test.network.RetrofitService

import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v7.widget.helper.ItemTouchHelper


class MainActivity : ParentActivity() {

    private lateinit var myApplication: MyApplication
    private var retrofitService: RetrofitService? = null
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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


        val itemTouchHelper = ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, p1: Int) {
                        val position = viewHolder.adapterPosition
                        articleAdapter.removeArticle(position)
                        myApplication.setArticles(articleAdapter.articles)
                    }

                    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                        return false
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

                    if (result != null) {
                        myApplication.setArticles(result.hits)

                        articleAdapter.articles = ArrayList(result.hits)
                        srlProducts.isRefreshing = false
                        pbArticles.hide()
                    }
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                articleAdapter.articles = ArrayList(myApplication.getArticles())
                srlProducts.isRefreshing = false
                pbArticles.hide()
            }
        })
    }
}