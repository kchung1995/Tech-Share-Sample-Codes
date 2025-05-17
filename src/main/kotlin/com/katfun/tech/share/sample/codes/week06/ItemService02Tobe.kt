package com.katfun.tech.share.sample.codes.week06

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service

@Service
class ItemService02Tobe(
    private val itemRepository: ItemRepository,
    private val itemRequestTransactionService: ItemRequestTransactionService,
    private val itemRemoteService: ItemRemoteService,
    private val userInformationRepository: UserInformationRepository,
    private val dispatcher: CoroutineDispatcher
) {
    fun refreshItem(accountId: Long): ItemRegisterResponse {
        val transactionId = itemRequestTransactionService.initRequest(accountId, ItemRequestType.REFRESH)
        val accountAuth = userInformationRepository.findAccountAuthInfo(accountId)
        val items = itemRepository.findAllByAccountId(accountId)    // 스코프 밖으로 분리
        CoroutineScope(dispatcher).launch {
            try {   // try-catch 블록을 코루틴 안으로 이동
                items.forEach { item ->
                    coroutineScope {    // 예외 전파를 위한 coroutineScope 사용
                        val response = itemRemoteService.refreshItem(accountId, item, accountAuth.name)
                        updateItem(item, response)
                    }
                }

                itemRequestTransactionService.setDone(transactionId)
            } catch (e: ItemRegisterError) {
                itemRequestTransactionService.setFail(transactionId, e.error)
            } catch (e: Exception) {
                itemRequestTransactionService.setFail(transactionId, ItemErrorType.READ_TIMEOUT)
            }
        }

        return ItemRegisterResponse(transactionId)
    }

    private fun updateItem(item: Item, itemSearchResponse: ItemSearchResponse) {

    }
}
