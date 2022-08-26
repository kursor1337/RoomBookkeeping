package com.kursor.roombookkeeping.domain.repositories

import com.kursor.roombookkeeping.model.Person

interface PersonRepository {

    fun get(id: Long): Person?

    fun getAll(): List<Person>

    fun save(person: Person)

    fun edit(person: Person)

    fun delete(person: Person)

}