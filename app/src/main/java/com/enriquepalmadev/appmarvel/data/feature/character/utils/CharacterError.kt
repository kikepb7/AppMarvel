package com.enriquepalmadev.appmarvel.data.feature.character.utils
//Esta clase seria de dominio y haria falta una en data(general).
sealed class CharacterError {
    //TODO clases objetos Errores
    data class ApiError(val code: Int, val message: String): CharacterError()
    data object Unauthorized: CharacterError()
    data object UnknownHostError: CharacterError()
}