package com.moji.fairfaxtest.presentation.presenters

import com.moji.fairfaxtest.data.RestApi.RestApi
import com.moji.fairfaxtest.domain.entities.NewsResponseView
import com.moji.fairfaxtest.presentation.Listeners.NewsListener
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by moji on 20/2/18.
 */

class NewsPresenter : Presenter<NewsListener> {
    private var listener: NewsListener? = null

    override fun attachListener(listener: NewsListener) {
        this.listener = listener
    }

    fun getNewsList() {
        listener?.let {
            it.showProgress("beep")
            RestApi.getEndpoints().askForNews()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<NewsResponseView> {
                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(response: NewsResponseView) {
                            it.onNewsFetched(response)
                            it.hideProgress()
                        }

                        override fun onError(e: Throwable) {
                            it.onError(e?.message ?: "beep")
                            it.hideProgress()
                        }

                        override fun onComplete() {
                        }
                    })
        }
    }
}

