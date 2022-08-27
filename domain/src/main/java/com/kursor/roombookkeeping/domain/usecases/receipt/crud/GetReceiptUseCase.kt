package com.kursor.roombookkeeping.domain.usecases.receipt.crud

import com.kursor.roombookkeeping.domain.repositories.ReceiptRepository
import com.kursor.roombookkeeping.model.Receipt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetReceiptUseCase(
    val receiptRepository: ReceiptRepository
) {

    suspend operator fun invoke(id: Long): Receipt? = withContext(Dispatchers.IO) {
        receiptRepository.get(id)
    }

}