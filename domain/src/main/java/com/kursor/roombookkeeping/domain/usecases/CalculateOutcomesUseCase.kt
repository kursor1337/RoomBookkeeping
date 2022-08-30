package com.kursor.roombookkeeping.domain.usecases

import com.kursor.roombookkeeping.model.Person
import com.kursor.roombookkeeping.model.Receipt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalculateOutcomesUseCase {

    suspend operator fun invoke(receipt: Receipt): Map<Person, Double> {
        return withContext(Dispatchers.Default) {
            receipt.persons.associateWith { person ->
                var outcomeValue = 0.0
                receipt.priceList.forEach { price ->
                    if (person !in price.persons) return@forEach
                    outcomeValue += price.value.toDouble() / price.persons.size
                }
                outcomeValue
            }
        }
    }

}