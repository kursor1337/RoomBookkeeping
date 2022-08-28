package com.kursor.roombookkeeping.domain.usecases.person

import com.kursor.roombookkeeping.domain.repositories.PersonRepository
import com.kursor.roombookkeeping.model.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddPersonUseCase(
    val personRepository: PersonRepository
) {

    suspend operator fun invoke(person: Person) = withContext(Dispatchers.IO) {
        personRepository.save(person)
    }

}