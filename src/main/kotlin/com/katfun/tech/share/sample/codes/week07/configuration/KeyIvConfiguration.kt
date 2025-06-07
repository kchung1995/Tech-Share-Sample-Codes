package com.katfun.tech.share.sample.codes.week07.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeyIvConfiguration {
    @Bean
    fun databaseKeyIv() = KeyIv("정", "카펀")
}

data class KeyIv(
    val key: String,
    val iv: String
)