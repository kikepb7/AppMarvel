package com.enriquepalmadev.domain_layer.feature.series.failure

sealed class FailureDomain {
    data class CustomErrorDomain(val code: String, val msg: String): FailureDomain()
    data object UnauthorizedErrorDomain: FailureDomain()
    data object EmptyErrorDomain: FailureDomain()
    data object UnknownHostErrorDomain: FailureDomain()
    data object CoroutineErrorDomain: FailureDomain()
}
