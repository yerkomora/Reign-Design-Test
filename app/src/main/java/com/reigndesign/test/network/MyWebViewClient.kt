package com.reigndesign.test.network

import android.webkit.WebView
import android.webkit.WebViewClient

class MyWebViewClient : WebViewClient() {

    interface OnListener {
        fun onLoad()
    }

    var onListener: OnListener? = null

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        if (onListener != null)
            onListener?.onLoad()
    }
}