package com.enriquepalmadev.appmarvel.data.feature.character.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.enriquepalmadev.appmarvel.data.feature.character.database.dao.CharacterDao
import com.enriquepalmadev.appmarvel.data.feature.character.database.entities.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class CharacterDatabase:RoomDatabase() {

    abstract fun getCharacterDao(): CharacterDao
}