package com.enriquepalmadev.domain_layer.feature.character.utils

sealed class CharacterErrorModel {
    data class ApiError(val code: Int, val message: String): CharacterErrorModel()
    data object Unauthorized: CharacterErrorModel()
    data object UnknownHostError: CharacterErrorModel()
}