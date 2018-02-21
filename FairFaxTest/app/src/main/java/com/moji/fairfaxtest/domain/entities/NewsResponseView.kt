package com.moji.fairfaxtest.domain.entities

import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Created by moji on 19/2/18.
 */

class NewsResponseView : Serializable {

    @SerializedName("assets")
    val newsViewList: List<NewsAssetView>? = null
}
