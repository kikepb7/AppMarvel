package com.enriquepalmadev.data_layer.feature.series.di

import com.enriquepalmadev.data_layer.feature.series.api.datasource.SerieRemoteDataSourceImpl
import com.enriquepalmadev.data_layer.feature.series.api.service.CustomInterceptor
import com.enriquepalmadev.data_layer.feature.series.api.service.IMarvelFilmSerieService
import com.enriquepalmadev.data_layer.feature.series.api.utils.Constants
import com.enriquepalmadev.data_layer.feature.series.repository.FilmSerieRepositoryImpl
import com.enriquepalmadev.domain_layer.feature.series.repository.IFilmSerieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun getRetrofitClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(CustomInterceptor())
        .addInterceptor(loggingInterceptor)
        .build()



    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getRetrofitClient())
            .build()
    }

    @Singleton
    @Provides
    fun provideIMarvelFilmSerieService(retrofit: Retrofit): IMarvelFilmSerieService {
        return retrofit.create(IMarvelFilmSerieService::class.java)
    }

    @Singleton
    @Provides
    fun provideSerieRemoteDataSource(serieService: IMarvelFilmSerieService): SerieRemoteDataSourceImpl {
        return SerieRemoteDataSourceImpl(serieService)
    }

    @Singleton
    @Provides
    fun provideSerieRepository(serieRemoteDataSourceImpl: SerieRemoteDataSourceImpl): IFilmSerieRepository {
        return FilmSerieRepositoryImpl(serieRemoteDataSourceImpl)
    }

}