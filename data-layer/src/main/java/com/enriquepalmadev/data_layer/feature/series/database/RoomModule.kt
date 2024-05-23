package com.enriquepalmadev.data_layer.feature.series.database

import android.content.Context
import androidx.room.Room
import com.enriquepalmadev.data_layer.feature.series.database.dao.SerieDao
import com.enriquepalmadev.data_layer.feature.series.database.datasource.SerieLocalDataSourceImpl
import com.enriquepalmadev.data_layer.feature.series.repository.LocalSerieRepositoryImpl
import com.enriquepalmadev.domain_layer.feature.series.repository.ILocalSerieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideSerieDatabase(@ApplicationContext appContext: Context): SerieDatabase {
        return Room.databaseBuilder(
            appContext,
            SerieDatabase::class.java,
            "series_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideSerieDao(serieDatabase: SerieDatabase) : SerieDao{
        return serieDatabase.serieDao()
    }

    @Singleton
    @Provides
    fun provideSerieLocalDataSource(serieDao: SerieDao) : SerieLocalDataSourceImpl{
        return SerieLocalDataSourceImpl(serieDao)
    }

    @Singleton
    @Provides
    fun provideLocalSerieRepository(serieLocalDataSourceImpl: SerieLocalDataSourceImpl): ILocalSerieRepository {
        return LocalSerieRepositoryImpl(serieLocalDataSourceImpl)
    }
}
