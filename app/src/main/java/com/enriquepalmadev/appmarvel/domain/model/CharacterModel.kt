package com.enriquepalmadev.appmarvel.domain.model

import java.io.Serializable

data class CharacterModel(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnailDTO: String
) : Serializable


//Mapear el DTO a esta clase
//Para utilizala en el ui



