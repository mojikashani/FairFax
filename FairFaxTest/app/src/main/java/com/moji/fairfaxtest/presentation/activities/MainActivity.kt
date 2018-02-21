package com.moji.fairfaxtest.presentation.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.moji.fairfaxtest.R
import com.moji.fairfaxtest.presentation.Listeners.NewsListener
import com.moji.fairfaxtest.presentation.presenters.NewsPresenter
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.moji.fairfaxtest.domain.entities.NewsAssetView
import com.moji.fairfaxtest.presentation.Listeners.NewsOnClickListener
import com.moji.fairfaxtest.presentation.recyclerviews.NewsAdapter


class MainActivity : AppCompatActivity() , NewsListener, NewsOnClickListener {
    companion object {
        val EXTRA_URL : String = "extra_url"
    }
    private val presenter = NewsPresenter(this)
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
        val intent : Intent = Intent(this, ArticleActivity::class.java)
        intent.putExtra(EXTRA_URL, url)
        startActivity(intent)
    }

    override fun hideProgress() {
        swipeRefreshNews.isRefreshing = false
    }

    override fun onAuthorizationError(e: Throwable) {
        Toast.makeText(this,getString(R.string.error_authentication_failed), Toast.LENGTH_LONG).show()
    }

    override fun onError(message: String?) {
        Toast.makeText(this,message?:getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show()
    }

    override fun onNoNetworkError() {
        Toast.makeText(this,getString(R.string.error_no_internet_connection), Toast.LENGTH_LONG).show()
    }

    override fun showProgress(message: String) {
        swipeRefreshNews.isRefreshing = true
    }
}
