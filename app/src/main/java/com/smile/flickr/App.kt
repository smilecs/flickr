package com.smile.flickr

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache

class App : android.app.Application() {

    val imageLoader: ImageLoader by lazy {
        ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }.build()
    }


    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private lateinit var appInstance: App
        fun getsInstance(): App = appInstance

        val appContext: Context
            get() = appInstance.applicationContext
    }
}