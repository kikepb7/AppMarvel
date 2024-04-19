package com.enriquepalmadev.appmarvel.data.feature.comics.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enriquepalmadev.appmarvel.domain.feature.comics.model.ComicModel

@Entity(tableName = "comic_table")
data class ComicEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "pageCount") val pageCount: Int,
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean = false
)



@Entity(tableName = "favorite_comics")
data class FavoriteComics(
    @PrimaryKey val comicId: Int
)


fun List<ComicEntity>.toDomainModel() : List<ComicModel> {
    return map {
        ComicModel(
            id = it.id,
            title = it.title,
            description = it.description,
            pageCount = it.pageCount,
            thumbnail = it.thumbnail
        )
    }
}