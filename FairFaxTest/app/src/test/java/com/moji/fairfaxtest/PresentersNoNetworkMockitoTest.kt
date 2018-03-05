package com.moji.fairfaxtest

import android.content.Context
import android.net.ConnectivityManager
import com.moji.fairfaxtest.data.rest.RestApi
import com.moji.fairfaxtest.presentation.Listeners.NewsListener
import com.moji.fairfaxtest.presentation.presenters.NewsPresenter

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.junit.Before
import org.mockito.*
import org.mockito.ArgumentMatchers.anyString
import org.mockito.junit.MockitoJUnitRunner


/**
 * Created by moji on 22/2/18.
 */
@RunWith(MockitoJUnitRunner::class)
class PresentersNoNetworkMockitoTest {

    @Mock
    private val mMockContext: Context = mock(Context::class.java)

    @Mock
    private val mMockNewsListener: NewsListener = mock(NewsListener::class.java)

    @InjectMocks
    lateinit var presenter : NewsPresenter

    @Before
    fun setUp() {
        /** mocking Context, ConnectivityManager and NetworkInfo so it assume there is
        no network connection **/
        val connectivityManager: ConnectivityManager = mock(ConnectivityManager::class.java)
        Mockito.`when`( mMockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn( connectivityManager )
        Mockito.`when`( connectivityManager.activeNetworkInfo).thenReturn( null )
    }

    /** test NewsPresenter behaviour on a unsuccessful scenario due to no active network
     *  presenter show progress view then invoke onNoNetworkError method then
     *  hide progress view**/
    @Test
    fun presenter_show_progress_invoke_onNoNetworkError_then_hide_progress() {
        presenter.getNewsList(true, RestApi.getEndpoints())
        Mockito.verify(mMockNewsListener).showProgress(anyString())
        Mockito.verify(mMockNewsListener).onNoNetworkError()
        Mockito.verify(mMockNewsListener).hideProgress()
    }
}

