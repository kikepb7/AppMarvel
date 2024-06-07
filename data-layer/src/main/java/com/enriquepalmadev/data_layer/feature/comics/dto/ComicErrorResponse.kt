package com.enriquepalmadev.data_layer.feature.comics.dto

data class FailureDto(val code: Int, val message: String?)
sealed class Failure

data class ApiError(val code: Int, val message: String): Failure()
data object Unauthorized: Failure()
data object UnknownHostError: Failure()

