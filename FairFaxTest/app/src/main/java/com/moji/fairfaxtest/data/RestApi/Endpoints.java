package com.moji.fairfaxtest.data.RestApi;

/**
 * Created by moji on 19/2/18.
 */

import com.moji.fairfaxtest.domain.entities.NewsResponseView;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Endpoints {
    @GET("1/coding_test/13ZZQX/full")
    Observable<NewsResponseView> askForNews();
}
