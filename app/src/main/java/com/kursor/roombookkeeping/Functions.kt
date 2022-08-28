package com.kursor.roombookkeeping

import com.kursor.roombookkeeping.model.Person
import com.kursor.roombookkeeping.model.Price

fun List<Price>.calculateCommonPersons(): List<Person> {
    val result = mutableSetOf<Person>()
    this.forEach { price ->
        price.persons.forEach { person ->
            result.add(person)
        }
    }
    return result.toList()
}