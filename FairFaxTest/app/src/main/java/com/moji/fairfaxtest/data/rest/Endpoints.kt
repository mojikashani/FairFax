package com.moji.fairfaxtest.data.rest

/**
 * Created by moji on 19/2/18.
 * ---------------------------------
 * Rest Api endpoint calls
 * We are using Retrofit and Reactive Java so our functions return
 * the response as observable objects
 */

import com.moji.fairfaxtest.domain.entities.NewsResponseView

import io.reactivex.Observable
import retrofit2.http.GET

interface Endpoints {
    @GET("1/coding_test/13ZZQX/full")
    fun askForNews(): Observable<NewsResponseView>
}
