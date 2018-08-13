package com.reigndesign.test.activities

import android.os.Bundle
import android.view.MenuItem
import android.webkit.DownloadListener
import android.webkit.WebViewClient

import com.reigndesign.test.R
import com.reigndesign.test.models.Article
import com.reigndesign.test.network.MyWebViewClient

import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Article

        val json = intent.getStringExtra(Article.ARTICLE)
        val article = Article()
        article.fromJson(json)

        // WebView

        if (article.urlValid()) {
            val myWebViewClient = MyWebViewClient()

            myWebViewClient.onListener = object : MyWebViewClient.OnListener {
                override fun onLoad() {
                    pbArticle.hide()
                }
            }

            wvArticle.webViewClient = myWebViewClient
            wvArticle.loadUrl(article.url)
        }
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
}