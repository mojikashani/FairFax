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
    var relatedImages: List<RelatedImageView>? = null

    // find the smallest image from related images
    // by calculating image size from its width and height
    fun getSmallestImage():RelatedImageView?{
        var min :Int = Int.MAX_VALUE;
        var smallestImage : RelatedImageView? = null
        relatedImages?.forEach {
            var h = it.height?:0
            var w = it.width?:0
            if(h*w < min){
                min = h*w
                smallestImage = it
            }

        }
        return smallestImage
    }
}
