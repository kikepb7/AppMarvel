package com.enriquepalmadev.data_layer.feature.character.utils

sealed class CharacterError {
    data class ApiError(val code: Int, val message: String): CharacterError()
    data object Unauthorized: CharacterError()
    data object UnknownHostError: CharacterError()
}