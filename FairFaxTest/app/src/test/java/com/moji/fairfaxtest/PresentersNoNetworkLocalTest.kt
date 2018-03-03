package com.moji.fairfaxtest

import android.content.Context
import android.net.ConnectivityManager
import com.moji.fairfaxtest.data.rest.RestApi
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
import org.junit.Assert
import org.junit.Before
import org.mockito.junit.MockitoJUnitRunner


/**
 * Created by moji on 22/2/18.
 */
@RunWith(MockitoJUnitRunner::class)
class PresentersNoNetworkLocalTest {

    @Mock
    private val mMockContext: Context = mock(Context::class.java)

    @Before
    fun setUp() {
        /** mocking Context, ConnectivityManager and NetworkInfo so it assume there is
        no network connection **/
        val connectivityManager: ConnectivityManager = mock(ConnectivityManager::class.java)
        Mockito.`when`( mMockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn( connectivityManager )
        Mockito.`when`( connectivityManager.activeNetworkInfo).thenReturn( null )
    }

    // Presenter calls onNoNetworkError if no network is available
    @Test
    fun presenter_calls_no_network_error_if_there_is_no_network() {
        val presenter = NewsPresenter(mMockContext)
        presenter.attachListener( object : NewsListener {
            override fun onNewsFetched(newsAssets: List<NewsAssetView>) {
                fail()
            }
            override fun hideProgress() {
            }

            override fun onError(message: String?) {
                fail()
            }

            override fun onNoNetworkError() {
                Assert.assertTrue(true)
            }

            override fun onAuthorizationError(e: Throwable) {
                fail()
            }

            override fun showProgress(message: String) {
            }
        })
        presenter.getNewsList(Schedulers.newThread(), RestApi.getEndpoints())
    }
}

