package com.smile.data.mapper

import com.past3.ketro.kcore.model.KMapper
import com.smile.data.FlickrResponse
import com.smile.data.ItemsResponse
import com.smile.domain.entity.Flickr

internal object FlickerMapper : KMapper<FlickrResponse, List<Flickr>>() {

    override fun mapFrom(from: FlickrResponse): List<Flickr> {
        val flickrList = from.items.map {
            Flickr(
                title = it.title,
                link = it.media.m,
                date = it.dateTaken,
                author = it.author
            )
        }
        return flickrList.sortedBy {
            it.date
        }
    }
}