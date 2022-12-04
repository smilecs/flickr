package com.smile.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface FlickrApi {

    @GET("/services/feeds/photos_public.gne")
    suspend fun getFeed(
        @Query("format") format: String = "json",
        @Query("nojsoncallback") callback: String = "1",
        @Query("tags") tags: String
    ): Response<FlickrResponse>
}