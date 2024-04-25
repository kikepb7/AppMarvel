package com.enriquepalmadev.appmarvel.data.feature.character.datasource

import com.enriquepalmadev.appmarvel.data.feature.character.utils.Constants.Companion.API_KEY
import com.enriquepalmadev.appmarvel.data.feature.character.utils.Constants.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    //Moverlo a datasource; Character
    private val httpClient = OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor()).build()
    val retrofitService: CharacterService by lazy {
        retrofitConnection().create(CharacterService::class.java)
    }

    private fun retrofitConnection(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }
}

class ApiKeyInterceptor(): Interceptor{
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