package com.smile.domain

import com.past3.ketro.kcore.model.KResponse
import com.smile.domain.entity.Flickr

interface FlickrDataSource {

    suspend fun getFeed(tags: String, tagMode: String): KResponse<List<Flickr>>
}