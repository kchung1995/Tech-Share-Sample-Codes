package com.katfun.tech.share.sample.codes.week05.sample01

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class AssetResponseFactoryTest {

    @Test
    fun `자산이 은행 계좌일 경우, 공유 상태이면 IllegalArgumentException이 발생한다`() {
        // Given: a shared BankAccount
        val sharedBankAccount = BankAccount(id = 1, isDisplay = true, isExpired = false, isShared = true)

        // When / Then: attempting to create a response should throw an IllegalArgumentException
        assertThatThrownBy { AssetResponseFactory.toResponseDto(sharedBankAccount) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `자산이 은행 계좌일 경우, 공유 상태가 아니면 예외를 던지지 않는다`() {
        // Given: a non-shared BankAccount
        val nonSharedBankAccount = BankAccount(id = 1, isDisplay = true, isExpired = false, isShared = false)

        // When: creating a response DTO
        val result = AssetResponseFactory.toResponseDto(nonSharedBankAccount)

        // Then: no exception is thrown and the response is correctly created
        assertThat(result).isNotNull
        assertThat(result.id.id).isEqualTo(nonSharedBankAccount.id)
    }
}