package com.enriquepalmadev.appmarvel.di

import com.enriquepalmadev.appmarvel.core.RetrofitBuilder
import com.enriquepalmadev.appmarvel.data.feature.series.api.service.IMarvelFilmSerieService
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton // Unique instance of Retrofit
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(RetrofitBuilder.getRetrofitClient())
            .build()
    }

    @Singleton
    @Provides
    fun provideIMarvelFilmSerieService(retrofit: Retrofit): IMarvelFilmSerieService{
        return retrofit.create(IMarvelFilmSerieService::class.java)
    }

}