package com.enriquepalmadev.data_layer.feature.series.api.service

import com.enriquepalmadev.data_layer.feature.series.api.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.newBuilder()
            .addQueryParameter("ts", Constants.TIMESTAMP)
            .addQueryParameter("apikey", Constants.API_KEY)
            .addQueryParameter("hash", Constants.hash())
            .build()

        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}