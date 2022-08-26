package com.kursor.roombookkeeping.domain.usecases

import com.kursor.roombookkeeping.domain.repositories.ReceiptRepository
import com.kursor.roombookkeeping.model.Receipt

class DeleteReceiptUseCase(
    val receiptRepository: ReceiptRepository
) {

    suspend operator fun invoke(receipt: Receipt) = receiptRepository.delete(receipt)

}