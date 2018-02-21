package com.moji.fairfaxtest.presentation.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.moji.fairfaxtest.R
import com.moji.fairfaxtest.domain.entities.NewsResponseView
import com.moji.fairfaxtest.presentation.Listeners.NewsListener
import com.moji.fairfaxtest.presentation.presenters.NewsPresenter

class MainActivity : AppCompatActivity() , NewsListener {

    private val presenter = NewsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachListener(this)
        presenter.getNewsList()
    }

    override fun onNewsFetched(response: NewsResponseView) {

    }

    override fun hideProgress() {

    }

    override fun onAuthorizationError(e: Throwable) {

    }

    override fun onError(message: String) {

    }

    override fun onNoNetworkError() {

    }

    override fun showProgress(message: String) {

    }
}
