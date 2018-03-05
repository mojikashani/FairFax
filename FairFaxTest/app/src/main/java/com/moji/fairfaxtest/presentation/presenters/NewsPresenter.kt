package com.moji.fairfaxtest.presentation.presenters

import android.content.Context
import com.moji.fairfaxtest.data.rest.RestApi
import com.moji.fairfaxtest.domain.entities.NewsResponseView
import com.moji.fairfaxtest.presentation.Listeners.NewsListener
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import android.net.ConnectivityManager
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.moji.fairfaxtest.data.rest.Endpoints


/**
 * Created by moji on 20/2/18.
 * ------------------------------
 * a presenter for calling news list api
 * its purpose is to handle all possible situation when calling an API
 * and separate the presentation concern from the data layer
 * and provide a neat an reliable class to be used within presentation layer
 */

class NewsPresenter(private val context : Context, private val listener : NewsListener) : Presenter<NewsListener> {

    // this method calls all api request and handle all possible scenarios
    fun getNewsList(runSynchronized: Boolean, endpoints : Endpoints?) {
        listener?.let {
            // Notify that a progress view should be shown now
            it.showProgress("")
            // if there is no network available onNoNetworkError will be called
            if(isNetworkAvailable()) {
                // calls api using reactive java and retrofit
                var observable = endpoints?.askForNews()
                // run synchronised if flag is false
                if(!runSynchronized) {
                    observable = observable
                            ?.subscribeOn(Schedulers.newThread())
                            ?.observeOn(AndroidSchedulers.mainThread())
                }
                observable?.subscribe(object : Observer<NewsResponseView> {
                            override fun onSubscribe(d: Disposable) {
                            }

                            // This is called when the call is successful
                            override fun onNext(response: NewsResponseView) {
                                // this sort the data by its time span and pass it to onNewsFetched
                                val soredList = response.newsViewList?.sortedByDescending { it.timeStamp } ?: emptyList()
                                it.onNewsFetched(soredList)
                                // Notify that the progress view should be hidden now
                                it.hideProgress()
                            }

                            // this is called when there is an error
                            override fun onError(e: Throwable) {
                                // if it is a 401 error onAuthorizationError will be called
                                if (e is HttpException && e.code() == 401) {
                                    it.onAuthorizationError(e)
                                }else {// otherwise onError will be called
                                    it.onError(e.message)
                                }
                                // Notify that the progress view should be hidden now
                                it.hideProgress()
                            }

                            override fun onComplete() {
                            }
                        })
            }else{
                it.onNoNetworkError()
                it.hideProgress()
            }
        }
    }

    fun getNewsList(){
        getNewsList(false, RestApi.getEndpoints())
    }
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null
    }
}

