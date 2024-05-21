package com.enriquepalmadev.data_layer.commons.di

import android.app.Application
import androidx.room.Room
import com.enriquepalmadev.data_layer.commons.interceptor.ApiKeyInterceptor
import com.enriquepalmadev.data_layer.commons.interceptor.NetworkErrorInterceptor
import com.enriquepalmadev.data_layer.commons.utils.Constants
import com.enriquepalmadev.data_layer.feature.character.datasource.CharacterRemoteDataSource
import com.enriquepalmadev.data_layer.feature.character.datasource.CharacterService
import com.enriquepalmadev.data_layer.feature.character.repository.CharacterRepositoryImpl
import com.enriquepalmadev.data_layer.feature.comics.database.ComicDatabase
import com.enriquepalmadev.data_layer.feature.comics.database.dao.ComicDao
import com.enriquepalmadev.data_layer.feature.comics.datasource.ComicDatabaseDataSource
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

    /*@Singleton
    @Provides
    fun provideComicRepository(comicRemoteDataSource: ComicRemoteDataSource): ComicRepository {
        return ComicRepositoryImpl(comicRemoteDataSource)
    }*/

    @Singleton
    @Provides
    fun provideDatabase(application: Application): ComicDatabase {
        return Room.databaseBuilder(application, ComicDatabase::class.java, "comic_db").fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideComicDatabaseDataSource(comicDao: ComicDao): ComicDatabaseDataSource {
        return ComicDatabaseDataSource(comicDao = comicDao)
    }

    @Singleton
    @Provides
    fun provideDao(database: ComicDatabase): ComicDao {
        return database.getComicDao()
    }

    @Provides
    @Singleton
    fun provideComicRepository(comicRemoteDataSource: ComicRemoteDataSource, comicDatabaseDataSource: ComicDatabaseDataSource): ComicRepository {
        return ComicRepositoryImpl(comicRemoteDataSource, comicDatabaseDataSource)
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
    fun provideCharacterRepository(characterRemoteDataSourceImpl: CharacterRemoteDataSource): CharacterRepository {
        return CharacterRepositoryImpl(characterRemoteDataSourceImpl)
    }
}
