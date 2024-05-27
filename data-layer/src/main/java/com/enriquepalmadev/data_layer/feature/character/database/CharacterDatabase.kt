package com.enriquepalmadev.data_layer.feature.character.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.enriquepalmadev.data_layer.feature.character.database.dao.CharacterDAO
import com.enriquepalmadev.data_layer.feature.character.database.entity.CharacterEntity
import com.enriquepalmadev.data_layer.feature.character.utils.extensions.Converters

@Database(entities = [CharacterEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDAO

    companion object {
        private const val DATABASE_NAME = "app_database.db"

        @Volatile
        private var INSTANCE: CharacterDatabase? = null

        fun getDatabase(context: Context): CharacterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharacterDatabase::class.java,
                    DATABASE_NAME
                )

                    .fallbackToDestructiveMigration() // Esto destruirá y recreará la base de datos si la versión cambia, útil para desarrollo
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
