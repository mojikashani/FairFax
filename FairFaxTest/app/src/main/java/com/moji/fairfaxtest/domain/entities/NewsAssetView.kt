package com.moji.fairfaxtest.domain.entities

import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Created by moji on 20/2/18.
 */

class NewsAssetView : Serializable {

    @SerializedName("headline")
    val headline: String? = null

    @SerializedName("theAbstract")
    val theAbstract: String? = null

    @SerializedName("byLine")
    val byLine: String? = null

    @SerializedName("url")
    val url: String? = null

    @SerializedName("timeStamp")
    val timeStamp: Long? = null

    @SerializedName("relatedImages")
    val relatedImages: List<RelatedImageView>? = null
}
