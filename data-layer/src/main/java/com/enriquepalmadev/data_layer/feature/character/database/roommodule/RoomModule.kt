package com.enriquepalmadev.data_layer.feature.character.database.roommodule

import android.content.Context
import androidx.room.Room
import com.enriquepalmadev.data_layer.feature.character.database.CharacterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val CHARACTER_DATABASE_NAME = "character_table"
    @Singleton
    @Provides
    fun provideCharacterRoom(@ApplicationContext context: Context) = Room.databaseBuilder(context, CharacterDatabase::class.java, CHARACTER_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideCharacterDao(databaseCharacter: CharacterDatabase) = databaseCharacter.getCharacterDao()

}