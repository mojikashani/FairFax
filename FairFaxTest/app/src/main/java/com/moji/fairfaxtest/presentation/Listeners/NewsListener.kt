package com.moji.fairfaxtest.presentation.Listeners

import com.moji.fairfaxtest.domain.entities.NewsResponseView

/**
 * Created by moji on 20/2/18.
 */

interface NewsListener : RestListener {
    fun onNewsFetched(response: NewsResponseView)
}
