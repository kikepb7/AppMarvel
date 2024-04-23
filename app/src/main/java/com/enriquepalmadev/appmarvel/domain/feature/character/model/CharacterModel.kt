package com.enriquepalmadev.appmarvel.domain.feature.character.model

import java.io.Serializable

data class CharacterModel(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnailDTO: String
) : Serializable    // Si lo quito me sale un Error que indica que la clase
// CharacterModel no implementa la interfaz Serializable ni Parcelable, que son necesarias para
// pasar objetos a través de fragments utilizando el Navigation Component.




