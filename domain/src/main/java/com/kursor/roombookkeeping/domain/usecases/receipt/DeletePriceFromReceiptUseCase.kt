package com.kursor.roombookkeeping.domain.usecases.receipt

import com.kursor.roombookkeeping.domain.repositories.ReceiptRepository
import com.kursor.roombookkeeping.model.Price
import com.kursor.roombookkeeping.model.Receipt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeletePriceFromReceiptUseCase(
    val receiptRepository: ReceiptRepository
) {

    suspend operator fun invoke(receipt: Receipt, price: Price) {
        withContext(Dispatchers.IO) {
            receiptRepository.edit(
                Receipt(
                    id = receipt.id,
                    name = receipt.name,
                    dateTime = receipt.dateTime,
                    priceList = receipt.priceList.toMutableList().apply {
                        remove(price)
                    }
                )
            )
        }

    }

}