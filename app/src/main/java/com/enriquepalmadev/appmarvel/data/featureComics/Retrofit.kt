package com.enriquepalmadev.appmarvel.data.featureComics

import com.enriquepalmadev.appmarvel.data.featureComics.service.ComicService
import com.enriquepalmadev.appmarvel.data.featureComics.utils.Constants.Companion.API_KEY
import com.enriquepalmadev.appmarvel.data.featureComics.utils.Constants.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private val httpClient = OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor()).build()
    val retrofitService : ComicService by lazy {
        retrofitConection().create(ComicService::class.java)
    }

    private fun retrofitConection(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
}

// para logcat (loggin interceptor)
class ApiKeyInterceptor() : Interceptor {
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