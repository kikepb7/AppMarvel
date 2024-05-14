package com.enriquepalmadev.data_layer.feature.series.api.utils

import com.enriquepalmadev.data_layer.feature.series.failure.CoroutineErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.CustomErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.EmptyErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.FailureData
import com.enriquepalmadev.data_layer.feature.series.failure.UnauthorizedErrorData
import com.enriquepalmadev.data_layer.feature.series.failure.UnknownHostErrorData
import com.enriquepalmadev.domain_layer.feature.series.failure.FailureDomain

fun FailureData.toFailureDomain(): FailureDomain {
    return when (this) {
        CoroutineErrorData -> FailureDomain.CoroutineErrorDomain
        is CustomErrorData -> FailureDomain.CustomErrorDomain(code = code, msg = msg)
        EmptyErrorData -> FailureDomain.EmptyErrorDomain
        UnauthorizedErrorData -> FailureDomain.UnauthorizedErrorDomain
        UnknownHostErrorData -> FailureDomain.UnknownHostErrorDomain
    }
}