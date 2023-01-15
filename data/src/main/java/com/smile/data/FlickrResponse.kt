package com.smile.data

import com.google.gson.annotations.SerializedName
import java.util.*


internal class FlickrResponse(
    val items: List<ItemsResponse>
)


internal class ItemsResponse(
    val title: String,
    val media: MediaLink,
    @SerializedName("date_taken")
    val dateTaken: Date,
    val author:String
)


internal class MediaLink(
    val m: String
)