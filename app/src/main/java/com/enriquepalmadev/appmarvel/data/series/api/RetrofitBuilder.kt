package com.enriquepalmadev.appmarvel.data.series.api

import com.enriquepalmadev.appmarvel.data.series.api.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    val retrofitService: IMarvelFilmSerieService by lazy {
        getRetrofit().create(IMarvelFilmSerieService::class.java)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun getRetrofit(): Retrofit {
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