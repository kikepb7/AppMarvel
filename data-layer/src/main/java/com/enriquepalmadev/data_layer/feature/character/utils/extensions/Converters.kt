package com.enriquepalmadev.data_layer.feature.character.utils.extensions

import androidx.room.TypeConverter
import com.enriquepalmadev.data_layer.feature.character.dto.ThumbnailDTO
import com.google.gson.Gson

class Converters {
    private val gson = Gson()

    //Characters
    @TypeConverter
    fun fromThumbnailDTO(thumbnailDTO: ThumbnailDTO?): String{
        return gson.toJson(thumbnailDTO)
    }

    @TypeConverter
    fun toThumbnailDTO(thumbnailJson: String?): ThumbnailDTO?{
        return gson.fromJson(thumbnailJson, ThumbnailDTO::class.java)
    }
}