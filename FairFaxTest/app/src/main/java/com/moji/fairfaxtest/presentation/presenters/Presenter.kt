package com.moji.fairfaxtest.presentation.presenters

import android.content.Context
import android.net.ConnectivityManager
import com.moji.fairfaxtest.presentation.Listeners.RestListener

/**
 * Created by moji on 20/2/18.
 * ------------------------------
 * presenters are to handle all possible situation when calling an API
 * and separate the presentation concern from the data layer
 * and provide a neat an reliable class to be used within presentation layer
 */
;
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.Serializable

abstract class Presenter<T : Serializable>(private val context : Context) {

    var runASynchronous = true

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null
    }

    protected fun callApi(baseObservable : Observable<T>, observer: Observer<T>, listener: RestListener){
        if (isNetworkAvailable()) {
            // run synchronised if flag is false
            var observable  = baseObservable
            if (runASynchronous) {
                observable = baseObservable
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
            }
            observable.subscribe(observer)
        } else {
            listener.onNoNetworkError()
        }
    }
}
