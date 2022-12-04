package com.smile.flickr.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.past3.ketro.kcore.model.KResponse
import com.smile.data.FlickrDataSourceFactory
import com.smile.domain.ErrorClass
import com.smile.domain.FlickrDataSource
import com.smile.domain.parseToErrorClass
import com.smile.flickr.FlickrUI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val flickrData: FlickrDataSource = FlickrDataSourceFactory.dataSource(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private val _flickrUIFlow: MutableStateFlow<FlickrUI> = MutableStateFlow(FlickrUI.Init)
    val flickrUIFlow = _flickrUIFlow as StateFlow<FlickrUI>


    fun getUIFeed(tags: String) {
        viewModelScope.launch(handler() + dispatcher) {
            when (val feed = flickrData.getFeed(tags)) {
                is KResponse.Success -> {
                    _flickrUIFlow.value = FlickrUI.UIData(feed.data ?: emptyList())
                }
                is KResponse.Failure -> {
                    _flickrUIFlow.value = FlickrUI.Error(feed.exception.parseToErrorClass())
                }
                else -> {
                    _flickrUIFlow.value = FlickrUI.Error(ErrorClass.GeneralError)
                }
            }
        }
    }

    private fun handler() = CoroutineExceptionHandler { _, throwable ->
        _flickrUIFlow.value = FlickrUI.Error(throwable.parseToErrorClass())
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return MainViewModel() as T
            }
        }
    }
}