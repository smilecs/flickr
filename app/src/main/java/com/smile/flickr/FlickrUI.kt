package com.smile.flickr

import com.smile.domain.ErrorClass
import com.smile.domain.entity.Flickr

sealed class FlickrUI {

    object Init : FlickrUI()

    class UIData(val uiList: List<Flickr>) : FlickrUI()

    class Error(val errorClass: ErrorClass) : FlickrUI()
}