package com.enriquepalmadev.appmarvel.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comic_table")
data class ComicEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "published") val published: String,
    @ColumnInfo(name = "writer") val writer: String,
    @ColumnInfo(name = "penciler") val penciler: String,
    // @ColumnInfo(name = "coverArtist") val coverArtist: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "image") val image: String
)