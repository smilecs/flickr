package com.smile.data

import com.past3.ketro.kcore.model.KResponse
import com.past3.ketro.kcore.model.mapObject
import com.past3.ketro.request.Request
import com.smile.data.mapper.FlickerMapper
import com.smile.domain.FlickrDataSource
import com.smile.domain.entity.Flickr
import retrofit2.Response

internal class FlickrDataSourceImpl(
    private val flickrApi: FlickrApi =
        NetModule.retrofit.create(FlickrApi::class.java)
) : FlickrDataSource {

    override suspend fun getFeed(tags: String, tagMode: String): KResponse<List<Flickr>> {
        val req = object : Request<FlickrResponse>(GeneralErrorHandler()) {
            override suspend fun apiRequest(): Response<out FlickrResponse> =
                flickrApi.getFeed(tags = tags, tagmode = tagMode)
        }.execute()

        return FlickerMapper.mapObject(req)
    }
}