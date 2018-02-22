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
import retrofit2.adapter.rxjava.HttpException


/**
 * Created by moji on 20/2/18.
 * ------------------------------
 * a presenter for calling news list api
 * its purpose is to handle all possible situation when calling an API
 * and separate the presentation concern from the data layer
 * and provide a neat an reliable class to be used within presentation layer
 */

class NewsPresenter(private val context : Context) : Presenter<NewsListener> {
    private var listener: NewsListener? = null

    // attach listener for its callbacks
    override fun attachListener(listener: NewsListener) {
        this.listener = listener
    }

    // this method calls all api request and handle all possible scenarios
    fun getNewsList() {
        listener?.let {
            // if there is no network available onNoNetworkError will be called
            if(isNetworkAvailable()) {
                // Notify that a progress view should be shown now
                it.showProgress("")
                // calls api using reactive java and retrofit
                RestApi.getEndpoints()?.askForNews()
                        ?.subscribeOn(Schedulers.newThread())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe(object : Observer<NewsResponseView> {
                            override fun onSubscribe(d: Disposable) {
                            }

                            // This is called when the call is successful
                            override fun onNext(response: NewsResponseView) {
                                // Notify that the progress view should be hidden now
                                it.hideProgress()
                                // this sort the data by its time span and pass it to onNewsFetched
                                val soredList = response.newsViewList?.sortedByDescending { it.timeStamp } ?: emptyList()
                                it.onNewsFetched(soredList)

                            }

                            // this is called when there is an error
                            override fun onError(e: Throwable) {
                                // Notify that the progress view should be hidden now
                                it.hideProgress()
                                // if it is a 401 error onAuthorizationError will be called
                                if (e is HttpException && e.code() == 401) {
                                    it.onAuthorizationError(e)
                                }else {// otherwise onError will be called
                                    it.onError(e?.message)
                                }
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

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null
    }
}

