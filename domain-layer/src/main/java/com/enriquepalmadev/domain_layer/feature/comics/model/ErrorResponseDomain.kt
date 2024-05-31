package com.enriquepalmadev.domain_layer.feature.comics.model

sealed class FailureDomain {
    data class ApiError(val code: Int, val message: String) : FailureDomain()
    data object Unauthorized : FailureDomain()
    data object UnknownHostError : FailureDomain()
}