package com.katfun.tech.share.sample.codes.week05.sample01

import com.katfun.tech.share.sample.codes.week05.sample01.Asset.Companion.sortById
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

        return (bankAccounts + stocks + insurances + cars)
            .filter { it.isActive() }
            .sortById()
    }
}

sealed interface Asset {
    val id: Long
    val isDisplay: Boolean
    val isExpired: Boolean

    fun isActive(): Boolean

    companion object {
        fun List<Asset>.sortById() = sortedBy { it.id }
    }
}

data class BankAccount(
    override val id: Long,
    override val isDisplay: Boolean,
    override val isExpired: Boolean,
    val isShared: Boolean
) : Asset {
    override fun isActive() = this.isExpired.not() && this.isDisplay && this.isShared.not()
}

data class Stocks(
    override val id: Long,
    override val isDisplay: Boolean,
    override val isExpired: Boolean,
    val isKorean: Boolean
) : Asset {
    override fun isActive() = this.isExpired.not() && this.isDisplay
}

data class Insurance(
    override val id: Long,
    override val isDisplay: Boolean,
    override val isExpired: Boolean,
    val isInsured: Boolean,
    val isPrize: Boolean
) : Asset {
    override fun isActive() = this.isExpired.not() && this.isDisplay && this.isPrize
}

data class Car(
    override val id: Long,
    override val isDisplay: Boolean,
    override val isExpired: Boolean,
) : Asset {
    override fun isActive(): Boolean = this.isExpired.not() && this.isDisplay
}

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