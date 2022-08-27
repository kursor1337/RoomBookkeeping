package com.kursor.roombookkeeping.domain.usecases.price

import com.kursor.roombookkeeping.domain.repositories.ReceiptRepository
import com.kursor.roombookkeeping.model.Price
import com.kursor.roombookkeeping.model.Receipt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EditPriceUseCase(
    val receiptRepository: ReceiptRepository,
    val receipt: Receipt
) {

    suspend operator fun invoke(index: Int, price: Price) {
        withContext(Dispatchers.IO) {
            receiptRepository.edit(
                Receipt(
                    id = receipt.id,
                    name = receipt.name,
                    dateTime = receipt.dateTime,
                    receipt.priceList.toMutableList().apply {
                        set(index, price)
                    }
                )
            )
        }
    }

}