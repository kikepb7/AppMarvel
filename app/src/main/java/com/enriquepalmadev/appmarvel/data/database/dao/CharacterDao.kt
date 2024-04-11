package com.enriquepalmadev.appmarvel.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enriquepalmadev.appmarvel.data.database.entities.CharacterEntity

@Dao
interface CharacterDao {
    @Query("select * from characters_table order by name desc")
    suspend fun  getAllCharacters():List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(charactersList: List<CharacterEntity>)

    @Query("delete from characters_table")
    suspend fun deleteAllCharactersFromLocal()
}