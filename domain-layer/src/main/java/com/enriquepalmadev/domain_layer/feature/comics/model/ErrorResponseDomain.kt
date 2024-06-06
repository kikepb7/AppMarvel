package com.enriquepalmadev.domain_layer.feature.comics.model

sealed class FailureDomain {
    data object ApiError : FailureDomain()
    data object Unauthorized : FailureDomain()
    data object UnknownHostError : FailureDomain()
}