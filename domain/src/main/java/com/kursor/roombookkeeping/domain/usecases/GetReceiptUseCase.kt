package com.kursor.roombookkeeping.domain.usecases

import com.kursor.roombookkeeping.domain.repositories.ReceiptRepository
import com.kursor.roombookkeeping.model.Receipt

class GetReceiptUseCase(
    val receiptRepository: ReceiptRepository
) {

    operator fun invoke(id: Long): Receipt? = receiptRepository.get(id)

}