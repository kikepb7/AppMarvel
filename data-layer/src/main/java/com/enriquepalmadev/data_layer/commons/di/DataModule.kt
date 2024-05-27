package com.enriquepalmadev.data_layer.commons.di

import com.enriquepalmadev.data_layer.commons.interceptor.ApiKeyInterceptor
import com.enriquepalmadev.data_layer.commons.interceptor.NetworkErrorInterceptor
import com.enriquepalmadev.data_layer.commons.utils.Constants
import com.enriquepalmadev.data_layer.feature.character.database.dao.CharacterDAO
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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module // Modules -> provides dependencies
@InstallIn(SingletonComponent::class) // Podemos declarar el alcance que queremos que tenga el módulo
object DataModule {
    @Singleton
    @Provides
    fun provideLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(ApiKeyInterceptor())
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(NetworkErrorInterceptor())
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
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
    fun provideCharacterService(retrofit: Retrofit): CharacterService {
        return retrofit.create(CharacterService::class.java)
    }

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(characterService: CharacterService): CharacterRemoteDataSource {
        return CharacterRemoteDataSource(characterService)
    }

    @Singleton
    @Provides
    fun provideCharacterRepository(
        characterRemoteDataSourceImpl: CharacterRemoteDataSource,
        characterDAO: CharacterDAO
    ): CharacterRepository {
        return CharacterRepositoryImpl(characterRemoteDataSourceImpl, characterDAO)
    }
}
