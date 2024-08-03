package com.katfun.tech.share.sample.codes.week05.sample01

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AssetsServiceTest {
    @Test
    fun `assets overall response test`() {
        // given
        val assets = AssetsService().overall(UserId(1L))

        // when
        // then
        val responses = assets.map {
            AssetResponseFactory.toResponseDto(it)
        }
    }
}
