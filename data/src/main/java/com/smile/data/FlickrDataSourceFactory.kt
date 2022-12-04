package com.smile.data

import com.smile.domain.FlickrDataSource

object FlickrDataSourceFactory {

    fun dataSource(): FlickrDataSource = FlickrDataSourceImpl()
}