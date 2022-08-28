package com.kursor.roombookkeeping.domain.usecases.person

import com.kursor.roombookkeeping.domain.repositories.PersonRepository
import com.kursor.roombookkeeping.model.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EditPersonNameUseCase(
    val personRepository: PersonRepository
) {

    suspend operator fun invoke(person: Person) = withContext(Dispatchers.IO) {
        personRepository.edit(person)
    }

    suspend operator fun invoke(personId: Long, newName: String) = invoke(
        Person(
            id = personId,
            name = newName
        )
    )
}
