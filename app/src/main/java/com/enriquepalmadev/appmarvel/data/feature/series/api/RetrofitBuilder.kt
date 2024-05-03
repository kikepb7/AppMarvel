package com.enriquepalmadev.appmarvel.data.feature.series.api

import com.enriquepalmadev.appmarvel.data.feature.series.api.service.CustomInterceptor
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getRetrofitClient())
            .build()
    }

    private fun getRetrofitClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(CustomInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()
}