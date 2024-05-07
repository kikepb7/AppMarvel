package com.enriquepalmadev.domain_layer.feature.character.utils
//Esta clase seria de dominio y haria falta una en data(general).
sealed class CharacterErrorDomain {
    //TODO clases objetos Errores
    data class ApiError(val code: Int, val message: String): CharacterErrorDomain()
    data object Unauthorized: CharacterErrorDomain()
    data object UnknownHostError: CharacterErrorDomain()
}