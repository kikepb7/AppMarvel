package com.enriquepalmadev.appmarvel.core

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitBuilder {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /*
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getRetrofitClient())
            .build()
    }

     */

    fun getRetrofitClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(CustomInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()


}