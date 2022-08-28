package com.kursor.roombookkeeping.domain.usecases.person

import com.kursor.roombookkeeping.domain.repositories.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPersonUseCase(
    val personRepository: PersonRepository
) {

    suspend operator fun invoke(id: Long) = withContext(Dispatchers.IO) {
        personRepository.get(id)
    }

}