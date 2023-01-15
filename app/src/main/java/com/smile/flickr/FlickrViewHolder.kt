package com.smile.flickr

import android.text.format.DateUtils
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import com.smile.domain.entity.Flickr
import com.smile.flickr.databinding.ListItemBinding

class FlickrViewHolder(
    private val item: ListItemBinding,
    private val req: ImageLoader,
    private val clickAction: (Flickr, FlickrEvent) -> Unit
) :
    RecyclerView.ViewHolder(item.root) {

    fun bind(data: Flickr) {
        item.title.text = data.title
        val request = ImageRequest.Builder(item.root.context)
            .data(data.link)
            .crossfade(true)
            .target(item.listImage)
            .build()
        req.enqueue(request)

        item.viewOnBrowser.setOnClickListener {
            clickAction(data, FlickrEvent.Browser)
        }
        item.viewOnFullScreen.setOnClickListener {
            clickAction(data, FlickrEvent.FullScreen)
        }
        item.dateTaken.text =
            DateUtils.formatDateTime(item.root.context, data.date.time, DateUtils.FORMAT_SHOW_DATE)
    }
}

sealed class FlickrEvent {
    object FullScreen : FlickrEvent()
    object Browser : FlickrEvent()
}