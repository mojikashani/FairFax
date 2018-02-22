package com.moji.fairfaxtest

import com.moji.fairfaxtest.data.rest.Endpoints
import com.moji.fairfaxtest.data.rest.RestApi
import com.moji.fairfaxtest.domain.entities.NewsResponseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.junit.Assert.*

import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

/**
 * Created by moji on 22/2/18.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class RestApiTest {

    private var endpoints: Endpoints? = null

    @Before
    fun setUp() {
        endpoints = RestApi.getEndpoints();
    }

    // This tests if the api retrieve at least one news asset
    @Test
    fun if_news_list_api_retrieve_at_least_one_news_asset() {
        if(endpoints != null) {
            endpoints?.askForNews()
                    ?.subscribe(object : Observer<NewsResponseView> {
                        override fun onSubscribe(d: Disposable) {
                        }
                        override fun onNext(response: NewsResponseView) {
                            assertTrue(response.newsViewList?.size ?: 0 >= 1)
                        }
                        override fun onError(e: Throwable) {
                            fail()
                        }
                        override fun onComplete() {
                        }
                    })
        }else{
            fail()
        }
    }

}

