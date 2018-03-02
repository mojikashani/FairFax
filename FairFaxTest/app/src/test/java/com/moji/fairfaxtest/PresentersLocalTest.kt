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
import org.mockito.junit.MockitoJUnitRunner


/**
 * Created by moji on 22/2/18.
 */
@RunWith(MockitoJUnitRunner::class)
class PresentersLocalTest {

    @Mock
    private val mMockContext: Context = mock(Context::class.java)


    // test if NewsPresenter return the api response on onNewsFetched
    @Test
    fun presenter_returns_at_least_one_new_asset() {
        //setup
        val connectivityManager: ConnectivityManager = mock(ConnectivityManager::class.java)
        val networkInfo: NetworkInfo = mock(NetworkInfo::class.java)
        Mockito.`when`( mMockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn( connectivityManager )
        Mockito.`when`( connectivityManager.activeNetworkInfo).thenReturn( networkInfo )
         // exercise
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

}
