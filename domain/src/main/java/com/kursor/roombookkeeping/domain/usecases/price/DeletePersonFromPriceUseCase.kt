package com.kursor.roombookkeeping.domain.usecases.price

import com.kursor.roombookkeeping.domain.repositories.ReceiptRepository
import com.kursor.roombookkeeping.model.Person
import com.kursor.roombookkeeping.model.Price
import com.kursor.roombookkeeping.model.Receipt

class DeletePersonFromPriceUseCase(
    val receiptRepository: ReceiptRepository,
    val receipt: Receipt
) {

    suspend operator fun invoke(index: Int, person: Person) {
        receiptRepository.edit(
            Receipt(
                id = receipt.id,
                name = receipt.name,
                dateTime = receipt.dateTime,
                priceList = receipt.priceList,
                persons = if (person in receipt.persons) receipt.persons
                else receipt.persons.toMutableList().apply {
                    add(person)
                }
            )
        )
    }

}