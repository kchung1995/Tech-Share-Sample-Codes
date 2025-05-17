package com.katfun.tech.share.sample.codes.week06.config

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DispatchersConfiguration {
    @Bean
    fun coroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}