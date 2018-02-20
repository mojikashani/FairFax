package com.moji.fairfaxtest.domain.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by moji on 20/2/18.
 */

public class NewsAssetView implements Serializable {

    @SerializedName("headline")
    private String headline;

    @SerializedName("theAbstract")
    private String theAbstract;

    @SerializedName("byLine")
    private String byLine;

    public String getHeadline() {
        return headline;
    }

    public String getTheAbstract() {
        return theAbstract;
    }

    public String getByLine() {
        return byLine;
    }
}
