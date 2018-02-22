package com.moji.fairfaxtest

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.moji.fairfaxtest.domain.entities.NewsAssetView
import com.moji.fairfaxtest.presentation.Listeners.NewsListener
import com.moji.fairfaxtest.presentation.presenters.NewsPresenter
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by moji on 22/2/18.
 */

@RunWith(AndroidJUnit4::class)
internal class PresentersTest{
    // test if NewsPresenter return the api response on onNewsFetched
    @Test
    fun presenter_returns_at_least_one_new_asset() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val presenter = NewsPresenter(appContext)
        presenter.attachListener( object : NewsListener{
            override fun onNewsFetched(newsAssets: List<NewsAssetView>) {
                assertTrue(newsAssets?.size >= 1)
            }
            override fun hideProgress() {
            }

            override fun onError(message: String?) {
                fail()
            }

            override fun onNoNetworkError() {
                fail()
            }

            override fun onAuthorizationError(e: Throwable) {
                fail()
            }

            override fun showProgress(message: String) {
            }
        })
        presenter.getNewsList()
    }

    // test if NewsPresenter calls showProgress to notify UI
    // during the api request
    @Test
    fun presenter_notify_to_show_progress_view() {
        val appContext = InstrumentationRegistry.getTargetContext()
        var isShowProgressCalled = false
        val presenter = NewsPresenter(appContext)
        presenter.attachListener( object : NewsListener{
            override fun onNewsFetched(newsAssets: List<NewsAssetView>) {
                if(!isShowProgressCalled) {
                    fail()
                }
            }
            override fun hideProgress() {
            }

            override fun onError(message: String?) {
            }

            override fun onNoNetworkError() {
            }

            override fun onAuthorizationError(e: Throwable) {
            }

            override fun showProgress(message: String) {
                isShowProgressCalled = true
            }
        })
        presenter.getNewsList()
    }

    // test if NewsPresenter calls hideProgress to notify UI
    // before calling onNewsFetch
    @Test
    fun presenter_notify_to_hide_progress_view() {
        val appContext = InstrumentationRegistry.getTargetContext()
        var isHideProgressCalled = false
        val presenter = NewsPresenter(appContext)
        presenter.attachListener( object : NewsListener{
            override fun onNewsFetched(newsAssets: List<NewsAssetView>) {
                if(!isHideProgressCalled){
                    fail()
                }
            }
            override fun hideProgress() {
                isHideProgressCalled = true
            }

            override fun onError(message: String?) {
            }

            override fun onNoNetworkError() {
            }

            override fun onAuthorizationError(e: Throwable) {
            }

            override fun showProgress(message: String) {
            }
        })
        presenter.getNewsList()
    }
}