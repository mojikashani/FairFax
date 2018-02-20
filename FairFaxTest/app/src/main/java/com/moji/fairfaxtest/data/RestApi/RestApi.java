package com.moji.fairfaxtest.data.RestApi;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by moji on 19/2/18.
 */

public class RestApi {
    private static Endpoints endpoints;

    private static Endpoints provideApiEndpoints() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bruce-v2-mob.fairfaxmedia.com.au/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit.create(Endpoints.class);
    }

    public static Endpoints getEndpoints() {
        if (endpoints == null) {
            endpoints = provideApiEndpoints();
        }
        return endpoints;
    }
}
