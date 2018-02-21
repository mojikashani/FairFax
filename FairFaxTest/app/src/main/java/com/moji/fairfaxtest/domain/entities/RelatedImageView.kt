package com.moji.fairfaxtest.domain.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by moji on 21/2/18.
 */

class RelatedImageView : Serializable {

    @SerializedName("url")
    val url: String? = null

    @SerializedName("width")
    val width: Int? = null

    @SerializedName("height")
    val height: Int? = null
}