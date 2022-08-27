package com.kursor.roombookkeeping.domain.usecases.receipt.add

import com.kursor.roombookkeeping.domain.repositories.ReceiptRepository
import com.kursor.roombookkeeping.model.Person
import com.kursor.roombookkeeping.model.Receipt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sun.rmi.server.Dispatcher

class AddPersonToReceiptUseCase(
    val receiptRepository: ReceiptRepository
) {

    suspend operator fun invoke(receipt: Receipt, person: Person) {
        withContext(Dispatchers.IO) {
            receiptRepository.edit(
                Receipt(
                    id = receipt.id,
                    name = receipt.name,
                    dateTime = receipt.dateTime,
                    priceList = receipt.priceList,
                    persons = receipt.persons.toMutableList().apply {
                        add(person)
                    }
                )
            )
        }

    }

}