package com.moji.fairfaxtest.presentation.recyclerviews

import android.content.Context
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
 */

class NewsAdapter(private var newsAssets: List<NewsAssetView>?, private var newsOnClickListener: NewsOnClickListener) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
        val txtHeadLine:TextView = layout.txtHeadLine
        val txtTheAbstract:TextView = layout.txtTheAbstract
        val txtByLine:TextView = layout.txtByLine
        val imgNews: ImageView = layout.imgNews
    }

    fun setData(_newsAssets: List<NewsAssetView>?) {
        _newsAssets?.let {
            newsAssets = _newsAssets
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): NewsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.view_holder_news, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asset = newsAssets?.get(position)
        if(asset != null) {
            holder.txtHeadLine.text = asset.headline
            holder.txtTheAbstract.text = asset.theAbstract
            holder.txtByLine.text = asset.byLine
            holder.layout.setOnClickListener {
                asset.url?.let {
                    newsOnClickListener.onNewsAssetClick(asset.url)
                }
            }
            asset?.relatedImages?.get(0)?.url?.let {
                Picasso.with(holder.layout.context).load(asset.relatedImages[0].url).into(holder.imgNews)
            }
        }
    }

    override fun getItemCount(): Int {
        return newsAssets?.size ?: 0
    }

}
