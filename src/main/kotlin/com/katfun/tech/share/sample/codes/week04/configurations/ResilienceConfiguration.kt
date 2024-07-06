package com.katfun.tech.share.sample.codes.week04.configurations

import feign.Target
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.cloud.openfeign.CircuitBreakerNameResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.reflect.Method
import java.net.MalformedURLException

@Configuration
class ResilienceConfiguration(
    private val registry: CircuitBreakerRegistry
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun circuitBreakerNameResolver(): CircuitBreakerNameResolver {
        return CustomCircuitBreakerNameResolver(registry)
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

@Configuration
class CustomCircuitBreakerNameResolver(
    private val registry: CircuitBreakerRegistry
) : CircuitBreakerNameResolver {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun resolveCircuitBreakerName(feignClientName: String?, target: Target<*>?, method: Method): String {
        val url: String? = target?.url()
        val circuitBreakerName =
            try {
                feignClientName + "_" + method.name
            } catch (e: MalformedURLException) {
                log.error("MalformedURLException : {}", url)
                "default"
            }

        if (registry.allCircuitBreakers.map { it.name }.contains(circuitBreakerName).not()) {
            registry.circuitBreaker(circuitBreakerName)
                .eventPublisher.onStateTransition { event ->
                    log.error(
                        "[{}] Changed CircuitBreaker State [{} -> {}]",
                        circuitBreakerName,
                        event.stateTransition.fromState,
                        event.stateTransition.toState
                    )
                }
            println("ㅁㄴㅇㄹ")
        }

        return circuitBreakerName
    }
}