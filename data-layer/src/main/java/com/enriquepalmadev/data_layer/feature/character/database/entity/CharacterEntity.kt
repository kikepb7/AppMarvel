package com.enriquepalmadev.data_layer.feature.character.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enriquepalmadev.domain_layer.feature.character.model.CharacterModel

@Entity(tableName = "characters_table")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String?,
    @ColumnInfo(name = "favourite") var favourite: Boolean = false
)
fun CharacterModel.toDatabase() = favourite?.let {
    CharacterEntity(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnailDTO,
        favourite = it
    )
}