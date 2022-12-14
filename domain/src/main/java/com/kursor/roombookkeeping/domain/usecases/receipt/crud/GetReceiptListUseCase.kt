package com.kursor.roombookkeeping.domain.usecases.receipt.crud

import com.kursor.roombookkeeping.domain.repositories.ReceiptRepository
import com.kursor.roombookkeeping.model.Receipt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetReceiptListUseCase(
    val receiptRepository: ReceiptRepository
) {

    suspend operator fun invoke(): List<Receipt> = withContext(Dispatchers.IO) {
        receiptRepository.getAll()
    }


}