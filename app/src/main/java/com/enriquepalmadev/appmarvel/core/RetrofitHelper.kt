package com.enriquepalmadev.appmarvel.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://gateway.marvel.com/v1/public/characters")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}