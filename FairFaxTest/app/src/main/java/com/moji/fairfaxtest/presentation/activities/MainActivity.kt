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

// NewsListener: used to listen to presenters call backs
// NewsOnClickListener: used to listen to recyclerView callbacks
class MainActivity : AppCompatActivity() , NewsListener, NewsOnClickListener {
    companion object {
        // used to pass extras to another activity through intents
        val EXTRA_URL : String = "extra_url"
    }
    // NewsPresenter: used to call APIs and separate presentation layer from data layer
    private val presenter = NewsPresenter(this)
    private val newsAdapter = NewsAdapter(emptyList(), this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // setting up presenters and assigning listener for its callbacks
        presenter.attachListener(this)
        // asking presenter to retrieve news list from the server
        // the result would be received through NewsListener callbacks
        presenter.getNewsList()

        setViews()
    }

    private fun setViews(){
        swipeRefreshNews.setOnRefreshListener{
            // whenever user pull the recyclerView down a new for news list will be sent
            // and the UI will be refreshed
            presenter.getNewsList()
        }

        // setting up RecyclerView and assigning its adapter to it
        recyclerNews.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerNews.layoutManager =layoutManager
        recyclerNews.adapter = newsAdapter
    }

    // this is called when requesting for news list is successful
    override fun onNewsFetched(newsAssets: List<NewsAssetView>) {
        newsAdapter.setData(newsAssets)
    }

    // this is called when users tap on "Read More>>" link
    override fun onNewsAssetClick(url: String) {
        // navigate user to web view activity and passing url as an extra string
        val intent : Intent = Intent(this, ArticleActivity::class.java)
        intent.putExtra(EXTRA_URL, url)
        startActivity(intent)
    }

    // this is called when presenter is done with api calling
    override fun hideProgress() {
        swipeRefreshNews.isRefreshing = false
    }

    // this is called when 401 error is sent from server
    // in certain circumstances this should navigate user to login page
    override fun onAuthorizationError(e: Throwable) {
        Toast.makeText(this,getString(R.string.error_authentication_failed), Toast.LENGTH_LONG).show()
    }

    // this is called when any error except for 401 and no network error happens
    // during the api call
    override fun onError(message: String?) {
        Toast.makeText(this,message?:getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show()
    }

    // this is called when there is no network during the api call
    override fun onNoNetworkError() {
        Toast.makeText(this,getString(R.string.error_no_internet_connection), Toast.LENGTH_LONG).show()
    }

    // this is called when the presenter starts sending request for data
    override fun showProgress(message: String) {
        swipeRefreshNews.isRefreshing = true
    }
}
