package com.moji.fairfaxtest

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.moji.fairfaxtest.data.rest.Endpoints
import com.moji.fairfaxtest.presentation.Listeners.NewsListener
import com.moji.fairfaxtest.presentation.presenters.NewsPresenter
import io.reactivex.Observable

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.junit.Before
import org.mockito.InjectMocks
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


/**
 * Created by moji on 22/2/18.
 */
@RunWith(MockitoJUnitRunner::class)
class PresentersErrorsMockitoTest {

    @Mock
    private val mMockContext: Context = mock(Context::class.java)

    @Mock
    private val mMockEndpoints: Endpoints = mock(Endpoints::class.java)

    @Mock
    private val mMockNewsListener: NewsListener = mock(NewsListener::class.java)

    @InjectMocks
    lateinit var presenter : NewsPresenter

    @Before
    fun setUp() {
        /** mocking Context, ConnectivityManager and NetworkInfo so it assume there is
        a network connection **/
        val connectivityManager: ConnectivityManager = mock(ConnectivityManager::class.java)
        val networkInfo: NetworkInfo = mock(NetworkInfo::class.java)
        Mockito.`when`( mMockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn( connectivityManager )
        Mockito.`when`( connectivityManager.activeNetworkInfo).thenReturn( networkInfo )
        // mocking endpoint, so whenever it gets called it throws an exception
        Mockito.`when`( mMockEndpoints.askForNews()).thenReturn( Observable.error(Throwable("Error")))

        presenter.runASynchronous = false
    }

    /** test NewsPresenter behaviour on a unsuccessful scenario due to api call exception
     *  presenter show progress view then invoke onError method then
     *  hide progress view**/
    @Test
    fun presenter_show_progress_invoke_onError_then_hide_progress() {
        presenter.getNewsList()
        verify(mMockNewsListener).showProgress(anyString())
        verify(mMockNewsListener).onError("Error")
        verify(mMockNewsListener).hideProgress()
    }
}

