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



/**
 * Created by moji on 20/2/18.
 */

class NewsPresenter(private val context : Context) : Presenter<NewsListener> {
    private var listener: NewsListener? = null

    override fun attachListener(listener: NewsListener) {
        this.listener = listener
    }

    fun getNewsList() {
        listener?.let {
            if(isNetworkAvailable()) {
                it.showProgress("")
                RestApi.getEndpoints()?.askForNews()
                        ?.subscribeOn(Schedulers.newThread())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe(object : Observer<NewsResponseView> {
                            override fun onSubscribe(d: Disposable) {
                            }

                            override fun onNext(response: NewsResponseView) {
                                val soredList = response.newsViewList?.sortedByDescending { it.timeStamp } ?: emptyList()
                                it.onNewsFetched(soredList)
                                it.hideProgress()
                            }

                            override fun onError(e: Throwable) {
                                if (e?.message != null && e.message?.contains("401") ?: false) {
                                    it.onAuthorizationError(e)
                                }
                                it.onError(e?.message)
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

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null
    }
}

