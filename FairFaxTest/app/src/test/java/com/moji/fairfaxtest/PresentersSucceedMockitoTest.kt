package com.moji.fairfaxtest

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.moji.fairfaxtest.data.rest.RestApi
import com.moji.fairfaxtest.domain.entities.NewsAssetView
import com.moji.fairfaxtest.presentation.Listeners.NewsListener
import com.moji.fairfaxtest.presentation.presenters.NewsPresenter
import com.nhaarman.mockito_kotlin.capture
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Before
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.ArgumentCaptor
import org.mockito.Captor




/**
 * Created by moji on 22/2/18.
 */
@RunWith(MockitoJUnitRunner::class)
class PresentersSucceedMockitoTest {

    @Mock
    private val mMockContext: Context = mock(Context::class.java)

    @Mock
    private val mMockNewsListener: NewsListener = mock(NewsListener::class.java)

    @InjectMocks
    lateinit var presenter : NewsPresenter

    @Captor
    private lateinit var captor: ArgumentCaptor<List<NewsAssetView>>

    @Before
    fun setUp() {
        /** mocking Context, ConnectivityManager and NetworkInfo so it assume there is
         a network connection **/
        val connectivityManager: ConnectivityManager = mock(ConnectivityManager::class.java)
        val networkInfo: NetworkInfo = mock(NetworkInfo::class.java)
        Mockito.`when`( mMockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn( connectivityManager )
        Mockito.`when`( connectivityManager.activeNetworkInfo).thenReturn( networkInfo )
    }

    /** test NewsPresenter behaviour on a successful scenario
     *  presenter show progress view then return at least an asset then
     *  hide progress view**/
    @Test
    fun presenter_show_progress_returns_a_new_asset_hide_progress() {
        presenter.getNewsList(true, RestApi.getEndpoints())
        verify(mMockNewsListener).showProgress(anyString())
        verify(mMockNewsListener).onNewsFetched(capture(captor))
        verify(mMockNewsListener).hideProgress()
        val capturedArgument = captor.value
        assertTrue(capturedArgument.isNotEmpty())
    }
}

