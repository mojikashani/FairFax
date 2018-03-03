package com.moji.fairfaxtest

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.moji.fairfaxtest.data.rest.Endpoints
import com.moji.fairfaxtest.domain.entities.NewsAssetView
import com.moji.fairfaxtest.presentation.Listeners.NewsListener
import com.moji.fairfaxtest.presentation.presenters.NewsPresenter
import io.reactivex.Observable
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
class PresentersErrorsLocalTest {

    @Mock
    private val mMockContext: Context = mock(Context::class.java)

    @Mock
    private val mMockEndpoints: Endpoints = mock(Endpoints::class.java)

    @Before
    fun setUp() {
        /** mocking Context, ConnectivityManager and NetworkInfo so it assume there is
        a network connection **/
        val connectivityManager: ConnectivityManager = mock(ConnectivityManager::class.java)
        val networkInfo: NetworkInfo = mock(NetworkInfo::class.java)
        Mockito.`when`( mMockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn( connectivityManager )
        Mockito.`when`( connectivityManager.activeNetworkInfo).thenReturn( networkInfo )
        // mocking endpoint, so whenever it gets called it throws an exception
        Mockito.`when`( mMockEndpoints.askForNews()).thenReturn( Observable.create(
        { emitter ->
            val throwable = Throwable("Error", null)
            emitter.onError(throwable)
        }) )
    }

    // Presenter calls onError if api call fails
    @Test
    fun presenter_calls_no_error_if_api_call_fails() {
        val presenter = NewsPresenter(mMockContext)
        presenter.attachListener( object : NewsListener {
            override fun onNewsFetched(newsAssets: List<NewsAssetView>) {
                fail()
            }
            override fun hideProgress() {
            }

            override fun onError(message: String?) {
                assertTrue(true)
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
        presenter.getNewsList(Schedulers.newThread(), mMockEndpoints)
    }

    /** test if NewsPresenter calls hideProgress to notify UI
    before calling onError **/
    @Test
    fun presenter_notify_to_hide_progress_view_after_error() {
        val presenter = NewsPresenter(mMockContext)
        var isHideProgressCalled = false
        presenter.attachListener( object : NewsListener{
            override fun onNewsFetched(newsAssets: List<NewsAssetView>) {
            }
            override fun hideProgress() {
                isHideProgressCalled = true
            }

            override fun onError(message: String?) {
                if(!isHideProgressCalled){
                    fail()
                }
            }

            override fun onNoNetworkError() {
            }

            override fun onAuthorizationError(e: Throwable) {
            }

            override fun showProgress(message: String) {
            }
        })
        presenter.getNewsList(Schedulers.newThread(), mMockEndpoints)
    }
}

