package com.katfun.tech.share.sample.codes.week04.configurations

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.cloud.openfeign.CircuitBreakerNameResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.reflect.Method

@Configuration
class ResilienceConfiguration(
    private val registry: CircuitBreakerRegistry
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun circuitBreakerNameResolver(): CircuitBreakerNameResolver {
        return CircuitBreakerNameResolver { feignClientName: String, _, method: Method -> feignClientName + "_" + method.name  }
    }

    @PostConstruct
    fun registerEvents() {
        registry.allCircuitBreakers.forEach {
            it.eventPublisher.onStateTransition { e ->
                log.error(
                    "[{}] Changed CircuitBreaker State [{} -> {}]",
                    it.name,
                    e.stateTransition.fromState,
                    e.stateTransition.toState
                )
            }
        }
    }
}
