package com.smile.flickr.ui.main

import com.past3.ketro.kcore.model.KResponse
import com.past3.ketro.kcore.model.StatusCode
import com.smile.domain.FlickrDataSource
import com.smile.domain.entity.Flickr
import java.io.IOException


object TestFailureDataSource : FlickrDataSource {

    override suspend fun getFeed(tags: String): KResponse<List<Flickr>> {
        return KResponse.Failure(exception = Exception(), statusCode = StatusCode(503))
    }
}


object TestThrownException : FlickrDataSource {

    override suspend fun getFeed(tags: String): KResponse<List<Flickr>> {
        throw IOException()
    }
}

object TestSuccessDataSource : FlickrDataSource {

    override suspend fun getFeed(tags: String): KResponse<List<Flickr>> {
        return KResponse.Success(listOf(Flickr("Test", "")), StatusCode(200))
    }
}