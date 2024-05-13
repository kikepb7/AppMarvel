package com.enriquepalmadev.data_layer.commons.di

import com.enriquepalmadev.data_layer.commons.utils.Constants
import com.enriquepalmadev.data_layer.feature.character.datasource.CharacterRemoteDataSource
import com.enriquepalmadev.data_layer.feature.character.datasource.CharacterService
import com.enriquepalmadev.data_layer.feature.character.repository.CharacterRepositoryImpl
import com.enriquepalmadev.data_layer.feature.comics.datasource.ComicRemoteDataSource
import com.enriquepalmadev.data_layer.feature.comics.repository.ComicRepositoryImpl
import com.enriquepalmadev.data_layer.feature.comics.service.ComicService
import com.enriquepalmadev.data_layer.feature.series.api.datasource.SerieRemoteDataSourceImpl
import com.enriquepalmadev.data_layer.feature.series.api.service.IMarvelFilmSerieService
import com.enriquepalmadev.data_layer.feature.series.repository.FilmSerieRepositoryImpl
import com.enriquepalmadev.domain_layer.feature.character.repository.CharacterRepository
import com.enriquepalmadev.domain_layer.feature.comics.ComicRepository
import com.enriquepalmadev.domain_layer.feature.series.repository.IFilmSerieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException
import javax.inject.Singleton

@Module // Modules -> provides dependencies
@InstallIn(SingletonComponent::class) // Podemos declarar el alcance que queremos que tenga el módulo
object DataModule {

    // 1. Attributes
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(ApiKeyInterceptor())
        addInterceptor(loggingInterceptor)
        addInterceptor(NetworkErrorInterceptor())
    }.build()

    class ApiKeyInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val url = request.url.newBuilder()
                .addQueryParameter("apikey", Constants.API_KEY)
                .addQueryParameter("hash", Constants.HASH)
                .addQueryParameter("ts", Constants.TS)
                .build()
            val newRequest = request.newBuilder()
                .url(url)
                .build()

            return chain.proceed(newRequest)
        }
    }

    class NetworkErrorInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()

            try {
                return chain.proceed(request)
            } catch (e: UnknownHostException) {
                throw e
            }
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Comics

    @Singleton
    @Provides
    fun provideComicsFromApi(retrofit: Retrofit): ComicService {
        return retrofit.create(ComicService::class.java)
    }

    @Singleton
    @Provides
    fun provideComicRemoteDataSource(comicService: ComicService): ComicRemoteDataSource {
        return ComicRemoteDataSource(comicService)
    }

    @Singleton
    @Provides
    fun provideComicRepository(comicRemoteDataSource: ComicRemoteDataSource): ComicRepository {
        return ComicRepositoryImpl(comicRemoteDataSource)
    }

    // Series

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

    // Characters
    @Singleton
    @Provides
    fun provideCharacterService(retrofit: Retrofit) : CharacterService {
        return retrofit.create(CharacterService::class.java)
    }

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(characterService: CharacterService): CharacterRemoteDataSource {
        return CharacterRemoteDataSource(characterService)
    }

    @Singleton
    @Provides
    fun provideCharacterRepository(characterRemoteDataSourceImpl: CharacterRemoteDataSource): CharacterRepository {
        return CharacterRepositoryImpl(characterRemoteDataSourceImpl)
    }
}