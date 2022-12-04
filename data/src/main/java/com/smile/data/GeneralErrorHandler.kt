package com.smile.data

import com.past3.ketro.request.ApiErrorHandler
import com.smile.domain.ErrorClass
import retrofit2.Response

class GeneralErrorHandler:ApiErrorHandler() {

    override fun getExceptionType(response: Response<*>): Exception {
        return when (response.code()) {
             in 400..503-> {
                 ErrorClass.GeneralError
             }
            else -> {
                ErrorClass.NoInternet
            }

        }
    }
}