package com.smile.flickr

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import com.bumptech.glide.RequestManager
import com.smile.domain.entity.Flickr
import com.smile.flickr.databinding.ListItemBinding

const val GRID_VIEW = 2
const val LINEAR_VIEW = 1

class FlickrAdapter(private val req: ImageLoader, private val clickAction: (String) -> Unit) :
    RecyclerView.Adapter<FlickrViewHolder>() {

    private val dataList: MutableList<Flickr> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrViewHolder {
        return FlickrViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), req, clickAction
        )
    }

    override fun getItemCount() = dataList.size

    fun update(data: List<Flickr>) {
        dataList.clear()
        dataList.addAll(data)
        calibrate()
    }

    fun calibrate() {
        notifyItemRangeChanged(0, dataList.size)
    }

    override fun onBindViewHolder(holder: FlickrViewHolder, position: Int) {
        holder.bind(dataList[position])
    }
}