package com.moji.fairfaxtest

import com.moji.fairfaxtest.domain.entities.NewsAssetView
import com.moji.fairfaxtest.domain.entities.RelatedImageView
import org.junit.Assert.*

import org.junit.Test


/**
 * Created by moji on 4/3/18.
 */
class RelatedImageJUnitTest {

    // check if getSmallestImage returns the smallest image
    @Test
    fun get_smallest_image_returns_the_smallest_image() {
        val newsAssetView = NewsAssetView()
        newsAssetView.relatedImages = listOf(
                RelatedImageView("url1", 50, 100)
                , RelatedImageView("url2", 150, 120)
                , RelatedImageView("url3", 90, 20)
                , RelatedImageView("url4", 560, 10))

        val theSmallestImage:String = newsAssetView.getSmallestImage()?.url?:""
        assertTrue(theSmallestImage == "url3")
    }
}

