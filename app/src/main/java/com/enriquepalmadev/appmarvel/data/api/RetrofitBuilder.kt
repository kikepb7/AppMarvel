package com.enriquepalmadev.appmarvel.data.api

import com.enriquepalmadev.appmarvel.data.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    val retrofitService: IMarvelFilmSerie by lazy {
        getRetrofit().create(IMarvelFilmSerie::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}