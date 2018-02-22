package com.moji.fairfaxtest.presentation.Listeners

import com.moji.fairfaxtest.domain.entities.NewsAssetView

/**
 * Created by moji on 20/2/18.
 * ----------------------------
 * list of callbacks specifically for NewsPresenter
 */

interface NewsListener : RestListener {
    fun onNewsFetched(newsAssets : List<NewsAssetView>)
}
