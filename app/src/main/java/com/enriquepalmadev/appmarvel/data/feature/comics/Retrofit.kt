package com.enriquepalmadev.appmarvel.data.feature.comics

import com.enriquepalmadev.appmarvel.data.feature.comics.service.ComicService
import com.enriquepalmadev.appmarvel.data.feature.comics.utils.Constants.Companion.API_KEY
import com.enriquepalmadev.appmarvel.data.feature.comics.utils.Constants.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException

object Retrofit {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(ApiKeyInterceptor())
        addInterceptor(loggingInterceptor)
        addInterceptor(NetworkErrorInterceptor())
    }.build()
    val retrofitService: ComicService by lazy {
        retrofitConnection().create(ComicService::class.java)
    }

    private fun retrofitConnection(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("apikey", API_KEY)
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

