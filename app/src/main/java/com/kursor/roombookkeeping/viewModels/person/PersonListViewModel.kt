package com.kursor.roombookkeeping.viewModels.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursor.roombookkeeping.domain.usecases.person.DeletePersonUseCase
import com.kursor.roombookkeeping.domain.usecases.person.GetPersonListUseCase
import com.kursor.roombookkeeping.model.Person
import kotlinx.coroutines.launch

class PersonListViewModel(
    val getPersonListUseCase: GetPersonListUseCase,
    val deletePersonUseCase: DeletePersonUseCase
) : ViewModel() {

    private val _personListLiveData = MutableLiveData<List<Person>>(emptyList())
    val personListLiveData: LiveData<List<Person>> get() = _personListLiveData

    fun loadData() {
        viewModelScope.launch {
            _personListLiveData.value = getPersonListUseCase()
        }
    }

    fun deletePerson(index: Int) {
        viewModelScope.launch {
            deletePersonUseCase(personListLiveData.value!![index])
            _personListLiveData.value = getPersonListUseCase()
        }
    }

}