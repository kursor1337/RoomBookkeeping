package com.kursor.roombookkeeping.domain.usecases

import com.kursor.roombookkeeping.domain.repositories.ReceiptRepository
import com.kursor.roombookkeeping.model.Receipt

class GetReceiptListUseCase(
    val receiptRepository: ReceiptRepository
) {

    suspend operator fun invoke(): List<Receipt> = receiptRepository.getAll()

}