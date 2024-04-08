package com.enriquepalmadev.appmarvel.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enriquepalmadev.appmarvel.domain.model.Character

@Entity(tableName = "characters_table")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "descripcion") val descripcion: String
)

fun Character.toDatabase() = CharacterEntity(nombre = nombre, image = image, descripcion = descripcion)

