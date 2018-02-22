package com.moji.fairfaxtest.domain.entities

import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Created by moji on 19/2/18.
 * -------------------------------
 * NEWS API RESPONSE entity used for converting jSon string to objects
 */

class NewsResponseView : Serializable {

    @SerializedName("assets")
    val newsViewList: List<NewsAssetView>? = null
}
