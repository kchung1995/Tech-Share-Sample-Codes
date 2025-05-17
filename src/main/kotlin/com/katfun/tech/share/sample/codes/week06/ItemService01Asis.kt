package com.katfun.tech.share.sample.codes.week06

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service

@Service
class ItemService01Asis(
    private val itemRepository: ItemRepository,
    private val itemRequestTransactionService: ItemRequestTransactionService,
    private val itemRemoteService: ItemRemoteService,
    private val userInformationRepository: UserInformationRepository,
    private val dispatcher: CoroutineDispatcher
) {
    fun refreshItem(accountId: Long): ItemRegisterResponse {
        val transactionId = itemRequestTransactionService.initRequest(accountId, ItemRequestType.REFRESH)
        val accountAuth = userInformationRepository.findAccountAuthInfo(accountId)

        try {
            CoroutineScope(dispatcher).launch {
                val items = itemRepository.findAllByAccountId(accountId)
                items.forEach {
                    val response = itemRemoteService.refreshItem(accountId, it, accountAuth.name)
                    updateItem(it, response)
                }

                itemRequestTransactionService.setDone(transactionId)
            }
        } catch (e: ItemRegisterError) {
            itemRequestTransactionService.setFail(transactionId, e.error)
        } catch (e: Exception) {
            itemRequestTransactionService.setFail(transactionId, ItemErrorType.READ_TIMEOUT)
        }

        return ItemRegisterResponse(transactionId)
    }

    private fun updateItem(item: Item, itemSearchResponse: ItemSearchResponse) {

    }
}
