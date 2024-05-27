package com.enriquepalmadev.data_layer.feature.character.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.enriquepalmadev.data_layer.feature.character.database.entity.CharacterEntity

@Dao
interface CharacterDAO {
    //Get all characters
    @Query("select * from characters_table where name not like '' and description not like '' and thumbnail not like 'http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg' order by name asc")
    suspend fun  getAllCharacters():List<CharacterEntity>?
    //Search by name
    @Query("select * from characters_table where name like :filt || '%' order by name asc")
    suspend fun getCharactersFilterlist(filt: String): List<CharacterEntity>?

    //Order by name
    @Query("select * from characters_table where name not like '' and description not like '' and thumbnail not like 'http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg' order by name asc")
    suspend fun getCharactersOrderbyNameAZ(): List<CharacterEntity>?

    @Query("select * from characters_table where name not like '' and description not like '' and thumbnail not like 'http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg' order by name desc")
    suspend fun getCharactersOrderbyNameZA(): List<CharacterEntity>?

    //Order by favourites
    @Query("select * from characters_table where name not like '' and description not like '' and thumbnail not like 'http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg' and favourite == true order by name asc")
    suspend fun getCharactersOrderByFavourites(): List<CharacterEntity>?

    //Get Detail character by id
    @Query("select * from characters_table where id = :id")
    suspend fun getCharacterDetail(id: Int): CharacterEntity?

    //Insert all characters
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(charactersList: List<CharacterEntity>)

    //Modifier character to favourite character
    @Update
    suspend fun updateFavouriteCharacter(character: CharacterEntity)

    //Delete all characters
    @Query("delete from characters_table")
    suspend fun deleteAllCharactersFromLocal()
}