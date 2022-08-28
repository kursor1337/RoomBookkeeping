package com.kursor.roombookkeeping.domain.usecases.person

import com.kursor.roombookkeeping.domain.repositories.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPersonListUseCase(
    val personRepository: PersonRepository
) {

    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        personRepository.getAll()
    }

}