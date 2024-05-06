package com.enriquepalmadev.data_layer.feature.series.api.utils

import com.enriquepalmadev.domain_layer.feature.series.utils.FailureDomain


fun Failure.toFailureDomain(): FailureDomain {
    return when (this) {
        CoroutineError -> FailureDomain.CoroutineError
        is CustomError -> FailureDomain.CustomError(code = code, msg = msg)
        EmptyError -> FailureDomain.EmptyError
        UnauthorizedError -> FailureDomain.UnauthorizedError
        UnknownHostError -> FailureDomain.UnknownHostError
    }
}