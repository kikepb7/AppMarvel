package com.enriquepalmadev.data_layer.commons.interceptor

import com.enriquepalmadev.data_layer.commons.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response
import java.net.UnknownHostException

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("apikey", Constants.API_KEY)
            .addQueryParameter("hash", Constants.HASH)
            .addQueryParameter("ts", Constants.TS)
            .build()
        val newRequest = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }
}

class NetworkErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        try {
            return chain.proceed(request)
        } catch (e: UnknownHostException) {
            throw e
        }
    }
}