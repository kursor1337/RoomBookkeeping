package com.kursor.roombookkeeping.domain.usecases.price

import com.kursor.roombookkeeping.domain.repositories.ReceiptRepository
import com.kursor.roombookkeeping.model.Person
import com.kursor.roombookkeeping.model.Price
import com.kursor.roombookkeeping.model.Receipt

class AddPersonToPriceUseCase(
    val receiptRepository: ReceiptRepository,
    val receipt: Receipt
) {

    suspend operator fun invoke(price: Price, person: Person) {
        receiptRepository.edit(
            Receipt(
                id = receipt.id,
                name = receipt.name,
                dateTime = receipt.dateTime,
                priceList = receipt.priceList.toMutableList(),
                persons = receipt.persons.toMutableList().apply {
                    add(person)
                }
            )
        )
    }

}