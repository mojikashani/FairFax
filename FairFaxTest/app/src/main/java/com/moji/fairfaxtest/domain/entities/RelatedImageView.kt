package com.moji.fairfaxtest.domain.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by moji on 21/2/18.
 * -------------------------------
 * RELATED IMAGE entity used for converting jSon string to objects
 */

class RelatedImageView : Serializable {

    @SerializedName("url")
    var url: String? = null

    @SerializedName("width")
    var width: Int? = null

    @SerializedName("height")
    var height: Int? = null

    constructor(_url:String, _width:Int, _height:Int){
        url = _url
        width = _width
        height = _height
    }
}