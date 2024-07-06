package com.katfun.tech.share.sample.codes.week04.configurations

import feign.Logger
import feign.RequestInterceptor
import feign.Retryer
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

class ExampleFeignConfiguration {
    @Bean
    fun feignLoggerLevel(): Logger.Level = Logger.Level.FULL

    @Bean
    fun feignHeaderInterceptor(): RequestInterceptor {
        val mediaType = MediaType.APPLICATION_JSON_VALUE
        return RequestInterceptor {
            it.header(HttpHeaders.CONTENT_TYPE, mediaType)
            it.header(HttpHeaders.ACCEPT, mediaType)
        }
    }

    @Bean
    fun retryer(): Retryer = Retryer.NEVER_RETRY
}