package com.reigndesign.test.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast

import com.reigndesign.test.R
import com.reigndesign.test.interfaces.ArticleInterface
import com.reigndesign.test.models.Article
import com.reigndesign.test.network.MyWebViewClient
import com.reigndesign.test.presenters.ArticlePresenter

import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : ParentActivity(), ArticleInterface.View {

    private lateinit var presenter: ArticleInterface.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Parameters

        val position = intent.getIntExtra(Article.POSITION, 0)

        // WebView

        val myWebViewClient = MyWebViewClient()

        myWebViewClient.onListener = object : MyWebViewClient.OnListener {
            override fun onLoad() {
                pbArticle.hide()
            }
        }

        wvArticle.webViewClient = myWebViewClient

        // Presenter

        presenter = ArticlePresenter(baseContext, this)
        presenter.create(position)
    }

    override fun onBackPressed() {
        wvArticle.stopLoading()
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // ArticleInterface.View

    override fun showArticle(article: Article) {
        wvArticle.loadUrl(article.url)
    }

    override fun showMessageUrlError() {
        Toast.makeText(this, R.string.url_error, Toast.LENGTH_SHORT).show()
    }
}