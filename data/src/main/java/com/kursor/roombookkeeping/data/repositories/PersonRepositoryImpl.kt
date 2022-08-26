package com.kursor.roombookkeeping.data.repositories

import com.kursor.roombookkeeping.data.database.daos.PersonDao
import com.kursor.roombookkeeping.data.database.entities.PersonEntity
import com.kursor.roombookkeeping.domain.repositories.PersonRepository
import com.kursor.roombookkeeping.model.Person

class PersonRepositoryImpl(
    val personDao: PersonDao
) : PersonRepository {
    override suspend fun get(id: Long): Person? = personDao.get(id)?.convertToModelEntity()

    override suspend fun getAll(): List<Person> = personDao.getAll().map { it.convertToModelEntity() }

    override suspend fun save(person: Person) = personDao.insert(person.convertToDatabaseEntity())

    override suspend fun edit(person: Person) = personDao.update(person.convertToDatabaseEntity())

    override suspend fun delete(person: Person) = personDao.delete(person.convertToDatabaseEntity())

    private fun PersonEntity.convertToModelEntity(): Person = Person(
        id = this.id,
        name = this.name
    )

    private fun Person.convertToDatabaseEntity(): PersonEntity = PersonEntity(
        id = this.id,
        name = this.name
    )

}