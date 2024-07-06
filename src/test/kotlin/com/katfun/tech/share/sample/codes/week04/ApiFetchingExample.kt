package com.katfun.tech.share.sample.codes.week04

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.katfun.tech.share.sample.codes.week04.api.ExampleApiFetcher
import com.katfun.tech.share.sample.codes.week04.api.ExampleApiRequest
import com.katfun.tech.share.sample.codes.week04.api.ExampleApiResponse
import feign.Request
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.encoding.HttpEncoding.CONTENT_TYPE
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.context.TestConstructor
import java.util.concurrent.TimeUnit

@EnableFeignClients
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWireMock(port = 0)
@SpringBootTest(
    properties = [
        "katfun.week04.host=http://localhost:\${wiremock.server.port}",
        "outbound-gw-host=",
        "outbound-gw-port=\${wiremock.server.port}",
        "url.week04.host=\${katfun.week04.host}",
        "resilience4j.timelimiter.configs.default.timeout-duration=2s"
    ]
)
class ApiFetchingExample(
    private val wireMockServer: WireMockServer,
    private val exampleApiFetcher: ExampleApiFetcher,
    private val circuitBreakerRegistry: CircuitBreakerRegistry,
    private val objectMapper: ObjectMapper
) {
    companion object {
        private const val CIRCUIT_BREAKER_NAME = "exampleApiFetcher_week04"
        private val exampleApiRequest = ExampleApiRequest(value = "malesuada")
    }

    @BeforeEach
    fun setup() {
        wireMockServer.start()
    }

    @AfterEach
    fun cleanup() {
        wireMockServer.stop()
        circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME).transitionToClosedState()
    }

    @Test
    fun `에러가 일정 이상 발생하면 CircuitBreaker Open`() {
        wireMockServer.stubFor(
            WireMock.post(WireMock.urlPathMatching( "/example/week04"))
                .willReturn(
                    WireMock.aResponse()
                        .withFixedDelay(2000)   // 2s delay
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(
                            objectMapper.writeValueAsString(
                                ExampleApiResponse(
                                    value = "vocent"
                                )
                            )
                        )
                )
        )

        val timeoutOptions = Request.Options(
            /* connectTimeout = */ 10000,
            /* connectTimeoutUnit = */ TimeUnit.MILLISECONDS,
            /* readTimeout = */ 1,
            /* readTimeoutUnit = */ TimeUnit.MILLISECONDS,
            /* followRedirects = */ false
        )

        // circuitBreaker 하나 생성
        exampleApiFetcher.week04(exampleApiRequest, timeoutOptions)

        assertThat(circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME).state).isEqualTo(CircuitBreaker.State.CLOSED)

        circuitBreakerRegistry.allCircuitBreakers.forEach { println(it) }

        (1..20).forEach { _ ->
            exampleApiFetcher.week04(exampleApiRequest, timeoutOptions)
        }

        assertThat(circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME).state).isEqualTo(CircuitBreaker.State.OPEN)
    }
}
