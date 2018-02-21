package com.moji.fairfaxtest.presentation.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.recyclerview.R.attr.layoutManager
import android.widget.Toast
import com.moji.fairfaxtest.R
import com.moji.fairfaxtest.domain.entities.NewsResponseView
import com.moji.fairfaxtest.presentation.Listeners.NewsListener
import com.moji.fairfaxtest.presentation.presenters.NewsPresenter
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.LinearLayoutManager
import com.moji.fairfaxtest.domain.entities.NewsAssetView
import com.moji.fairfaxtest.presentation.Listeners.NewsOnClickListener
import com.moji.fairfaxtest.presentation.recyclerviews.NewsAdapter


class MainActivity : AppCompatActivity() , NewsListener, NewsOnClickListener {
    private val presenter = NewsPresenter()
    private val newsAdapter = NewsAdapter(emptyList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // setting up presenters
        presenter.attachListener(this)
        presenter.getNewsList()
        setViews()
    }

    private fun setViews(){
        swipeRefreshNews.setOnRefreshListener{
            presenter.getNewsList()
        }

        recyclerNews.setHasFixedSize(true)
        // use a linear layout manager
        val layoutManager = LinearLayoutManager(this)
        recyclerNews.layoutManager =layoutManager
        recyclerNews.adapter = newsAdapter
    }

    override fun onNewsFetched(newsAssets: List<NewsAssetView>) {
        newsAdapter.setData(newsAssets)
    }

    override fun onNewsAssetClick(url: String) {
        Toast.makeText(applicationContext, url, Toast.LENGTH_LONG).show()
    }

    override fun hideProgress() {
        swipeRefreshNews.isRefreshing = false
    }

    override fun onAuthorizationError(e: Throwable) {

    }

    override fun onError(message: String) {

    }

    override fun onNoNetworkError() {

    }

    override fun showProgress(message: String) {
        swipeRefreshNews.isRefreshing = true
    }
}
