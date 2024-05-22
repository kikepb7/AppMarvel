package com.enriquepalmadev.data_layer.feature.series.database

import android.content.Context
import androidx.room.Room
import com.enriquepalmadev.data_layer.feature.series.database.dao.SerieDao
import com.enriquepalmadev.data_layer.feature.series.database.datasource.SerieLocalDataSourceImpl
import com.enriquepalmadev.data_layer.feature.series.repository.LocalSerieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideSerieDatabase(@ApplicationContext appContext: Context): SerieDatabase {
        return Room.databaseBuilder(
            appContext,
            SerieDatabase::class.java,
            "series_database"
        ).build()
    }
    @Provides
    @Singleton
    fun provideSerieDao(serieDatabase: SerieDatabase) : SerieDao{
        return serieDatabase.serieDao()
    }
    @Provides
    @Singleton
    fun provideLocalSerieRepository(serieDao: SerieLocalDataSourceImpl): LocalSerieRepositoryImpl {
        return LocalSerieRepositoryImpl(serieDao)
    }
}
