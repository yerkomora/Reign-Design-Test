package com.reigndesign.test.adapters

import android.support.v4.os.ConfigurationCompat
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.reigndesign.test.R
import com.reigndesign.test.models.Article

import kotlinx.android.synthetic.main.item_article.view.*
import java.text.SimpleDateFormat
import java.util.*

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    var articles: ArrayList<Article> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun removeArticle(position: Int) {
        articles.removeAt(position)
        notifyItemRemoved(position)
    }

    interface OnListener {
        fun onClick(article: Article)
    }

    var onListener: OnListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]

        article.position = position

        holder.title.text = article.getStoryOrTitle()

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
        val time = simpleDateFormat.parse(article.created_at).time
        val now = System.currentTimeMillis()

        val ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)

        val subTitle = "${article.author} - $ago"
        holder.subTitle.text = subTitle

        holder.view.setOnClickListener { onListener?.onClick(article) }
    }

    override fun getItemCount(): Int = articles.size

    class ViewHolder(val view: View) :
            RecyclerView.ViewHolder(view) {

        val title: AppCompatTextView = view.tvTitle
        val subTitle: AppCompatTextView = view.tvSubTitle

    }
}