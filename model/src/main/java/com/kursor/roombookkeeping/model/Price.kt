package com.kursor.roombookkeeping.model

data class Price(
    val name: String,
    val value: Int,
    val persons: List<Person>
)


fun List<Price>.calculateCommonPersons(): List<Person> {
    val result = mutableSetOf<Person>()
    this.forEach { price ->
        price.persons.forEach { person ->
            result.add(person)
        }
    }
    return result.toList()
}