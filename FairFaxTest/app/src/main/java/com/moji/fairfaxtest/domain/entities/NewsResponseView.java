package com.moji.fairfaxtest.domain.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by moji on 19/2/18.
 */

public class NewsResponseView implements Serializable {

    @SerializedName("assets")
    private List<NewsAssetView> assets;

    public List<NewsAssetView> getNewsViewList() {
        return assets;
    }
}
