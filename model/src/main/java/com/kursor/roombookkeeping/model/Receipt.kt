package com.kursor.roombookkeeping.model

import java.util.*

data class Receipt(
    val id: Long,
    val name: String,
    val dateTime: Date,
    val priceList: List<Price>,
) {

    val persons: List<Person>
        get() {
            val result = mutableSetOf<Person>()
            priceList.forEach { price ->
                price.persons.forEach { person ->
                    result.add(person)
                }
            }
            return result.toList()
        }

}