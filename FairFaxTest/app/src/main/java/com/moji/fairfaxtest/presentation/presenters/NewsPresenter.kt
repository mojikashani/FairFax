package com.moji.fairfaxtest.presentation.presenters

import android.content.Context
import com.moji.fairfaxtest.domain.entities.NewsResponseView
import com.moji.fairfaxtest.presentation.Listeners.NewsListener
import com.moji.fairfaxtest.data.rest.Endpoints
import com.moji.fairfaxtest.data.rest.RestApi


/**
 * Created by moji on 20/2/18.
 * ------------------------------
 * a presenter for calling news list api
 * its purpose is to handle all possible situation when calling an API
 * and separate the presentation concern from the data layer
 * and provide a neat an reliable class to be used within presentation layer
 */

class NewsPresenter(private val context: Context, private val endpoints: Endpoints, private val listener: NewsListener)
    : Presenter<NewsResponseView>(context) {
    constructor(context: Context,  listener: NewsListener): this(context,RestApi.getEndpoints(), listener)

    // this method calls all api request and handle all possible scenarios
    fun getNewsList() {
        callApi(endpoints.askForNews(), NewsObserver(listener), listener)
    }

    private class NewsObserver(private var listener: NewsListener) : BaseViewSubscriber<NewsResponseView, NewsListener>(listener) {
        override fun onSucceed(response: NewsResponseView) {
            // this sort the data by its time span and pass it to onNewsFetched
            val soredList = response.newsViewList?.sortedByDescending { it.timeStamp }
                    ?: emptyList()
            listener.onNewsFetched(soredList)
        }
    }

}

