package com.moji.fairfaxtest.presentation.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient

import com.moji.fairfaxtest.R

/**
 * Created by moji on 7/3/18.
 */

class ArticleFragment : ToolbarFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onStart() {
        super.onStart()
        // getting the article url from arguments and load it into a webView
        val url = arguments.getString(MainFragment.EXTRA_URL)
        val webViewNews = view.findViewById<WebView>(R.id.webViewNews)
        webViewNews.webViewClient = WebViewClient()
        webViewNews.loadUrl(url)
        // set the toolbar title
        setToolbarTitle(getString(R.string.article_fragment_title))
    }

}