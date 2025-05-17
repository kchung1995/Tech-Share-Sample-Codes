package com.katfun.tech.share.sample.codes.week06

import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

interface ItemRepository {
    fun findAllByAccountId(accountId: Long): List<Item>
}

@Repository
class ItemRepositoryImpl : ItemRepository {
    override fun findAllByAccountId(accountId: Long): List<Item> {
        TODO("Not yet implemented")
    }
}

interface ItemRequestTransactionService {
    fun initRequest(accountId: Long, requestType: ItemRequestType): String

    fun setDone(transactionId: String)

    fun setFail(transactionId: String, failCode: ItemErrorType)
}

@Service
class ItemRequestTransactionServiceImpl : ItemRequestTransactionService {
    override fun initRequest(
        accountId: Long,
        requestType: ItemRequestType
    ): String {
        TODO("Not yet implemented")
    }

    override fun setDone(transactionId: String) {
        TODO("Not yet implemented")
    }

    override fun setFail(transactionId: String, failCode: ItemErrorType) {
        TODO("Not yet implemented")
    }
}

interface ItemRemoteService {
    fun refreshItem(accountId: Long, item: Item, userName: String): ItemSearchResponse
}

@Service
class ItemRemoteServiceImpl : ItemRemoteService {
    override fun refreshItem(
        accountId: Long,
        item: Item,
        userName: String
    ): ItemSearchResponse {
        TODO("Not yet implemented")
    }
}

interface UserInformationRepository {
    fun findAccountAuthInfo(accountId: Long): UserInformation
}

@Repository
class UserInformationRepositoryImpl : UserInformationRepository {
    override fun findAccountAuthInfo(accountId: Long): UserInformation {
        TODO("Not yet implemented")
    }
}

data class UserInformation(
    val name: String
)

class ItemRegisterError(val error: ItemErrorType) : RuntimeException()

enum class ItemRequestType {
    REFRESH
    ;
}

enum class ItemErrorType {
    READ_TIMEOUT
    ;
}

data class Item(
    val id: Long
)

data class ItemSearchResponse(
    val response: String
)

data class ItemRegisterResponse(val transactionId: String)