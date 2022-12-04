package com.smile.flickr

import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import com.smile.domain.entity.Flickr
import com.smile.flickr.databinding.ListItemBinding

class FlickrViewHolder(
    private val item: ListItemBinding,
    private val req: ImageLoader,
    private val clickAction: (String) -> Unit
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

        item.listImage.setOnClickListener {
            clickAction(data.link)
        }
    }
}