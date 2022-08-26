package com.kursor.roombookkeeping.domain.usecases

import com.kursor.roombookkeeping.domain.repositories.ReceiptRepository
import com.kursor.roombookkeeping.model.Receipt

class UpdateReceiptUseCase(
    val receiptRepository: ReceiptRepository
) {

    operator fun invoke(receipt: Receipt) = receiptRepository.edit(receipt)

}