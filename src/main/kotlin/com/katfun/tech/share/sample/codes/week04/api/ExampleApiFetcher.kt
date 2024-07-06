package com.katfun.tech.share.sample.codes.week04.api

import com.katfun.tech.share.sample.codes.week04.configurations.ExampleFeignConfiguration
import com.katfun.tech.share.sample.codes.week04.configurations.readValueWithDefault
import feign.Request.Options
import org.slf4j.LoggerFactory
import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PostMapping
import java.util.concurrent.TimeUnit

@FeignClient(
    name = "exampleApiFetcher",
    url = "\${katfun.week04.host}",
    path = "/example",
    configuration = [ExampleFeignConfiguration::class],
    fallbackFactory = ExampleFallbackFactory::class
)
interface ExampleApiFetcher {
    @PostMapping("/week04")
    fun week04(
        requestBody: ExampleApiRequest,
        options: Options = ExampleApiFetcherOptions.week04
    ): ExampleApiResponse
}

@Component
class ExampleFallbackFactory : FallbackFactory<ExampleApiFetcher> {
    override fun create(cause: Throwable): ExampleApiFetcher {
        return ExampleFallback(cause)
    }
}

class ExampleFallback(private val cause: Throwable) : ExampleApiFetcher {
    private val log = LoggerFactory.getLogger(this::class.java)

    companion object {
        private const val FALLBACK_MESSAGE = "어쩔폴백"
    }

    override fun week04(requestBody: ExampleApiRequest, options: Options): ExampleApiResponse {
        log.warn(cause.message)

        return readValueWithDefault(
            cause,
            defaultInternal = ExampleApiResponse(FALLBACK_MESSAGE),
            timeoutFallback = ExampleApiResponse(FALLBACK_MESSAGE)
        )
    }
}

data class ExampleApiRequest(
    val value: String
)

data class ExampleApiResponse(
    val value: String
)

object ExampleApiFetcherOptions {
    private val timeUnitMilliseconds = TimeUnit.MILLISECONDS

    val week04 = Options(
        /* connectTimeout = */ 3000,
        /* connectTimeoutUnit = */ timeUnitMilliseconds,
        /* readTimeout = */ 10000,
        /* readTimeoutUnit = */ timeUnitMilliseconds,
        /* followRedirects = */ false
    )
}