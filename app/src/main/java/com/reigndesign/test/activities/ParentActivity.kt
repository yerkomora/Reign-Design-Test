package com.reigndesign.test.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.reigndesign.test.models.Article

abstract class ParentActivity : AppCompatActivity() {

    // Activities

    fun goArticle(article: Article) {
        val intent = Intent(this, ArticleActivity::class.java)
        intent.putExtra(Article.ARTICLE, article.toJson())
        startActivity(intent)
    }
}