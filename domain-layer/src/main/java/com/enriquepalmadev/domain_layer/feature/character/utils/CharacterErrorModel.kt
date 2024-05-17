package com.enriquepalmadev.domain_layer.feature.character.utils
//Esta clase seria de dominio y haria falta una en data(general).
sealed class CharacterErrorModel {
    //TODO clases objetos Errores
    data class ApiError(val code: Int, val message: String): CharacterErrorModel()
    data object Unauthorized: CharacterErrorModel()
    data object UnknownHostError: CharacterErrorModel()
}