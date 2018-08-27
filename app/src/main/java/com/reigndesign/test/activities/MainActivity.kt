package com.reigndesign.test.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.Toast

import com.reigndesign.test.R
import com.reigndesign.test.adapters.ArticleAdapter
import com.reigndesign.test.adapters.MySimpleCallback
import com.reigndesign.test.interfaces.ArticlesInterface
import com.reigndesign.test.models.Article
import com.reigndesign.test.presenters.ArticlesPresenter

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ParentActivity(), ArticlesInterface.View {

    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var presenter: ArticlesPresenter

    // ParentActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = ArticlesPresenter(baseContext, this)

        // RecyclerView

        rvArticles.setHasFixedSize(true)

        articleAdapter = ArticleAdapter()

        articleAdapter.onListener = object : ArticleAdapter.OnListener {
            override fun onClick(article: Article) {
                presenter.clickArticle(article)
            }
        }

        // ItemTouchHelper

        val itemTouchHelper = ItemTouchHelper(
                object : MySimpleCallback(0, ItemTouchHelper.LEFT) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, p1: Int) {
                        val position = viewHolder.adapterPosition

                        presenter.swipeArticle(position)
                    }
                })

        itemTouchHelper.attachToRecyclerView(rvArticles)

        rvArticles.adapter = articleAdapter

        presenter.create()

        // SwipeRefreshLayout

        srlProducts.setOnRefreshListener { presenter.refreshArticles() }
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    // ArticlesInterface.View

    override fun showArticles(articles: ArrayList<Article>) {
        articleAdapter.articles = articles

        srlProducts.isRefreshing = false
        pbArticles.hide()
    }

    override fun showMessageOffLine() {
        Toast.makeText(this, R.string.offline, Toast.LENGTH_SHORT).show()
    }

    override fun removeArticle(position: Int) {
        articleAdapter.removeArticle(position)
    }

    override fun showRemoveFailed() {
        Toast.makeText(this, R.string.remove_failed, Toast.LENGTH_SHORT).show()
    }

    override fun goArticle(position: Int) {
        val intent = Intent(this, ArticleActivity::class.java)
        intent.putExtra(Article.POSITION, position)
        startActivity(intent)
    }

    override fun showMessageNotUrl() {
        Toast.makeText(this, R.string.url_not, Toast.LENGTH_SHORT).show()
    }
}