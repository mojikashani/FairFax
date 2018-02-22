package com.moji.fairfaxtest.presentation.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.moji.fairfaxtest.R
import kotlinx.android.synthetic.main.activity_article.*

/**
 * Created by moji on 21/2/18.
 * -------------------------------
 * Activity to show news url content
 */

class ArticleActivity  : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        // getting url from intent and loading that to a web view
        val url = intent.getStringExtra(MainActivity.EXTRA_URL)
        webViewNews.loadUrl(url)
    }


}
