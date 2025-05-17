package com.katfun.tech.share.sample.codes.week06

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service

@Service
class ItemService03Tobe(
    private val itemRepository: ItemRepository,
    private val itemRequestTransactionService: ItemRequestTransactionService,
    private val itemRemoteService: ItemRemoteService,
    private val userInformationRepository: UserInformationRepository,
    private val dispatcher: CoroutineDispatcher
) {
    fun refreshItem(accountId: Long): ItemRegisterResponse {
        val transactionId = itemRequestTransactionService.initRequest(accountId, ItemRequestType.REFRESH)
        val accountAuth = userInformationRepository.findAccountAuthInfo(accountId)
        val items = itemRepository.findAllByAccountId(accountId)

        CoroutineScope(dispatcher).launch {
            try {
                coroutineScope {
                    items.forEach { item ->
                        launch(dispatcher) {
                            val response = itemRemoteService.refreshItem(accountId, item, accountAuth.name)
                            updateItem(item, response)
                        }
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
