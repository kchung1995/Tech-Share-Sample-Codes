package com.katfun.tech.share.sample.codes.week05.sample01

import org.springframework.stereotype.Service

@Service
class AssetsService(
    val assetsRepository: AssetsRepository = AccountsInMemoryClient()
) {
    /**
     * 사용자의 자산을 가져 오고, 각 카테고리별 active한 상태를 필터링한 후, id를 기준으로 정렬한다.
     */
    fun overall(userId: UserId): List<Asset> {
        val bankAccounts = assetsRepository.bankAccounts(userId = userId)
        val stocks = assetsRepository.stocks(userId = userId)
        val insurances = assetsRepository.insurances(userId = userId)
        val cars = assetsRepository.cars(userId = userId)

        return (bankAccounts.filter { it.isExpired.not() && it.isDisplay && it.isShared.not() } +
                stocks.filter { it.isExpired.not() && it.isDisplay } +
                insurances.filter { it.isExpired.not() && it.isDisplay && it.isPrize } +
                cars.filter { it.isExpired.not() && it.isDisplay })
            .sortedBy { it.id }
    }
}

sealed interface Asset {
    val id: Long
    val isDisplay: Boolean
    val isExpired: Boolean
}

data class BankAccount(
    override val id: Long,
    override val isDisplay: Boolean,
    override val isExpired: Boolean,
    val isShared: Boolean
) : Asset

data class Stocks(
    override val id: Long,
    override val isDisplay: Boolean,
    override val isExpired: Boolean,
    val isKorean: Boolean
) : Asset

data class Insurance(
    override val id: Long,
    override val isDisplay: Boolean,
    override val isExpired: Boolean,
    val isInsured: Boolean,
    val isPrize: Boolean
) : Asset

data class Car(
    override val id: Long,
    override val isDisplay: Boolean,
    override val isExpired: Boolean,
) : Asset

@JvmInline
value class UserId(val id: Long)

interface AssetsRepository {
    fun bankAccounts(userId: UserId): List<BankAccount>

    fun stocks(userId: UserId): List<Stocks>

    fun insurances(userId: UserId): List<Insurance>

    fun cars(userId: UserId): List<Car>
}

class AccountsInMemoryClient() : AssetsRepository {
    override fun bankAccounts(userId: UserId): List<BankAccount> = AccountsInMemoryStub.bankAccounts

    override fun stocks(userId: UserId): List<Stocks> = AccountsInMemoryStub.stocks

    override fun insurances(userId: UserId): List<Insurance> = AccountsInMemoryStub.insurances

    override fun cars(userId: UserId): List<Car> = AccountsInMemoryStub.cars
}

object AccountsInMemoryStub {
    val bankAccounts =
        (1L..20L step 3).map {
            BankAccount(
                id = it,
                isDisplay = it % 2 == 0L,
                isExpired = false,
                isShared = it % 3 != 0L
            )
        }
    val stocks =
        (1L..16L step 4).map { Stocks(id = it, isDisplay = it % 2 == 0L, isExpired = false, isKorean = it % 3 != 0L) }
    val insurances = (1L..12L step 3).map {
        Insurance(
            id = it,
            isDisplay = it % 4 == 0L,
            isExpired = false,
            isInsured = it % 3 != 0L,
            isPrize = it % 2 == 0L
        )
    }
    val cars = (1L..24L step 6).map { Car(id = it, isDisplay = it % 2 == 0L, isExpired = false) }
}