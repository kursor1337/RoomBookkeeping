package com.kursor.roombookkeeping.domain.usecases.receipt.crud

import com.kursor.roombookkeeping.domain.repositories.ReceiptRepository
import com.kursor.roombookkeeping.model.Receipt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteReceiptUseCase(
    val receiptRepository: ReceiptRepository
) {

    suspend operator fun invoke(receipt: Receipt) = withContext(Dispatchers.IO) {
        receiptRepository.delete(receipt)
    }

}