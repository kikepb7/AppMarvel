package com.enriquepalmadev.appmarvel.data.series.api

import com.enriquepalmadev.appmarvel.data.series.api.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    val retrofitService: IMarvelFilmSerieService by lazy {
        getRetrofit().create(IMarvelFilmSerieService::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getRetrofitClient())
            .build()
    }

    private fun getRetrofitClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(ParameterInterceptor())
            .build()
}