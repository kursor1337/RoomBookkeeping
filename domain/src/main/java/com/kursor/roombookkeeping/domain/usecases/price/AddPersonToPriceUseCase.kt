package com.kursor.roombookkeeping.domain.usecases.price

import com.kursor.roombookkeeping.domain.repositories.ReceiptRepository
import com.kursor.roombookkeeping.model.Person
import com.kursor.roombookkeeping.model.Price
import com.kursor.roombookkeeping.model.Receipt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddPersonToPriceUseCase(
    val receiptRepository: ReceiptRepository
) {

    suspend operator fun invoke(receipt: Receipt, price: Price, person: Person) {
        withContext(Dispatchers.IO) {
            receiptRepository.edit(
                Receipt(
                    id = receipt.id,
                    name = receipt.name,
                    dateTime = receipt.dateTime,
                    priceList = receipt.priceList.toMutableList().apply {
                        val index = this.indexOf(price)
                        set(index, Price(
                            name = price.name,
                            value = price.value,
                            persons = price.persons.toMutableList().apply {
                                add(person)
                            }
                        )
                        )
                    }
                )
            )
        }
    }
}