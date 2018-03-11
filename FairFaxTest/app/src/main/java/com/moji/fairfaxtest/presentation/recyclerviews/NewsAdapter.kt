package com.moji.fairfaxtest.presentation.recyclerviews

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.moji.fairfaxtest.R
import com.moji.fairfaxtest.domain.entities.NewsAssetView
import com.moji.fairfaxtest.presentation.Listeners.NewsOnClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_holder_news.view.*

/**
 * Created by moji on 21/2/18.
 * ----------------------------------
 * RecyclerView Adapter for News RecyclerView
 */
// a reference to NewsOnClickListener is needed so Adapter can communicate with activities
class NewsAdapter(private var newsAssets: List<NewsAssetView>?, private var newsOnClickListener: NewsOnClickListener) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var data: List<NewsAssetView>? = null
        set(_data) {
            newsAssets = _data
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): NewsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.view_holder_news, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asset = newsAssets?.get(position)
        if (asset != null) {
            holder.txtHeadLine.text = asset.headline
            holder.txtTheAbstract.text = asset.theAbstract
            holder.txtByLine.text = asset.byLine
            // when user tap "Read More>>" link onNewsAssetClick will be called
            holder.txtReadMore.setOnClickListener {
                asset.url?.let {
                    newsOnClickListener.onNewsAssetClick(asset.url)
                }
            }
            // getting the smallest image from related image
            val smallestImage = asset.getSmallestImage()
            smallestImage?.url?.let {
                // Picasso handles image catching very well
                Picasso.with(holder.layout.context).load(smallestImage.url).into(holder.imgNews)
            }
        }
    }

    override fun getItemCount(): Int {
        return newsAssets?.size ?: 0
    }

    inner class ViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
        val txtHeadLine: TextView = layout.txtHeadLine
        val txtTheAbstract: TextView = layout.txtTheAbstract
        val txtByLine: TextView = layout.txtByLine
        val imgNews: ImageView = layout.imgNews
        val txtReadMore: TextView = layout.txtReadMore
    }

}
