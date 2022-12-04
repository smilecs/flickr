package com.smile.flickr.ui.main

import com.smile.domain.ErrorClass
import com.smile.domain.FlickrDataSource
import com.smile.flickr.FlickrUI
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Test
import kotlin.properties.Delegates


internal class MainViewModelTest {

    private var mainViewModel: MainViewModel by Delegates.notNull()

    private fun setUp(dataSource: FlickrDataSource) {
        mainViewModel =
            MainViewModel(flickrData = dataSource, dispatcher = UnconfinedTestDispatcher())
    }

    @Test
    fun `getUIFeed emits appropriate FlickrUI on success of datasource`() {
        setUp(TestSuccessDataSource)

        mainViewModel.getUIFeed("test")

        val flickrData = mainViewModel.flickrUIFlow.value as FlickrUI.UIData

        Assert.assertTrue(flickrData.uiList[0].title == "Test")
    }

    @Test
    fun `getUIFeed emits appropriate FlickrUI on Error of datasource`() {
        setUp(TestFailureDataSource)

        mainViewModel.getUIFeed("test")

        val flickrData = mainViewModel.flickrUIFlow.value as FlickrUI.Error

        Assert.assertTrue(flickrData.errorClass == ErrorClass.GeneralError)
    }

    @Test
    fun `getUIFeed emits NoInternetException on thrown exception of datasource`() {
        setUp(TestThrownException)

        mainViewModel.getUIFeed("test")

        val flickrData = mainViewModel.flickrUIFlow.value as FlickrUI.Error

        Assert.assertTrue(flickrData.errorClass == ErrorClass.NoInternet)
    }
}