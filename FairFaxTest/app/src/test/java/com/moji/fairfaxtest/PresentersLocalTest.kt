package com.moji.fairfaxtest

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.moji.fairfaxtest.domain.entities.NewsAssetView
import com.moji.fairfaxtest.presentation.Listeners.NewsListener
import com.moji.fairfaxtest.presentation.presenters.NewsPresenter
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.mockito.junit.MockitoJUnitRunner


/**
 * Created by moji on 22/2/18.
 */
@RunWith(MockitoJUnitRunner::class)
class PresentersLocalTest {

    @Mock
    private val mMockContext: Context = mock(Context::class.java)

    @Before
    fun setUp() {
        /** mocking Context, ConnectivityManager and NetworkInfo so it assume there is
         a network connection **/
        val connectivityManager: ConnectivityManager = mock(ConnectivityManager::class.java)
        val networkInfo: NetworkInfo = mock(NetworkInfo::class.java)
        Mockito.`when`( mMockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn( connectivityManager )
        Mockito.`when`( connectivityManager.activeNetworkInfo).thenReturn( networkInfo )
    }

    // test if NewsPresenter return the api response on onNewsFetched
    @Test
    fun presenter_returns_at_least_one_new_asset() {
        val presenter = NewsPresenter(mMockContext)
        presenter.attachListener( object : NewsListener {
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
        presenter.getNewsList(Schedulers.newThread())
    }

    // test if NewsPresenter calls showProgress to notify UI
    // during the api request
    @Test
    fun presenter_notify_to_show_progress_view() {
        val presenter = NewsPresenter(mMockContext)
        var isShowProgressCalled = false
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
        presenter.getNewsList(Schedulers.newThread())
    }

    // test if NewsPresenter calls hideProgress to notify UI
    // before calling onNewsFetch
    @Test
    fun presenter_notify_to_hide_progress_view() {
        val presenter = NewsPresenter(mMockContext)
        var isHideProgressCalled = false
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
        presenter.getNewsList(Schedulers.newThread())
    }

}

