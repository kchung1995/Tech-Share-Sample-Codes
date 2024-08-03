package com.katfun.tech.share.sample.codes.week05.sample01

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BankAccountTest {

    @Test
    fun `노출 상태가 아닌 계좌는 활성 상태가 아니다`() {
        // Given: a bank account that is not displayed
        val account = BankAccount(id = 1, isDisplay = false, isExpired = false, isShared = false)

        // When: we check if the account is active
        val isActive = account.isActive()

        // Then: the account should not be active
        assertThat(isActive).isFalse
    }

    @Test
    fun `만료된 계좌는 활성 상태가 아니다`() {
        // Given: a bank account that is expired
        val account = BankAccount(id = 1, isDisplay = true, isExpired = true, isShared = false)

        // When: we check if the account is active
        val isActive = account.isActive()

        // Then: the account should not be active
        assertThat(isActive).isFalse
    }

    @Test
    fun `공유 계좌는 활성 상태가 아니다`() {
        // Given: a bank account that is shared
        val account = BankAccount(id = 1, isDisplay = true, isExpired = false, isShared = true)

        // When: we check if the account is active
        val isActive = account.isActive()

        // Then: the account should not be active
        assertThat(isActive).isFalse
    }

    @Test
    fun `만료되지 않았으며, 공유 계좌가 아니며, 노출 상태인 계좌는 활성 상태이다`() {
        // Given: a bank account that is not expired, not shared, and displayed
        val account = BankAccount(id = 1, isDisplay = true, isExpired = false, isShared = false)

        // When: we check if the account is active
        val isActive = account.isActive()

        // Then: the account should be active
        assertThat(isActive).isTrue
    }
}