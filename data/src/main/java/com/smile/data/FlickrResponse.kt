package com.smile.data


internal class FlickrResponse(
    val items: List<ItemsResponse>
)


internal class ItemsResponse(
    val title: String,
    val media: MediaLink
)


internal class MediaLink(
    val m: String
)