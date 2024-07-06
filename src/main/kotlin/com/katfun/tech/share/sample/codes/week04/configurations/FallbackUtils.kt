package com.katfun.tech.share.sample.codes.week04.configurations

import feign.RetryableException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

inline fun <reified T> readValueWithDefault(cause: Throwable, defaultInternal: T, timeoutFallback: T): T {
    return try {
        when {
            cause is TimeoutException || cause is SocketTimeoutException -> timeoutFallback
            cause is RetryableException && (cause.cause is TimeoutException || cause.cause is SocketTimeoutException) -> timeoutFallback
            else -> defaultInternal
        }
    } catch (e: Exception) {
        defaultInternal
    }
}