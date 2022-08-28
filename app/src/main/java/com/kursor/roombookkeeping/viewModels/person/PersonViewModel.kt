package com.kursor.roombookkeeping.viewModels.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursor.roombookkeeping.domain.usecases.person.AddPersonUseCase
import com.kursor.roombookkeeping.domain.usecases.person.EditPersonNameUseCase
import com.kursor.roombookkeeping.domain.usecases.person.GetPersonUseCase
import com.kursor.roombookkeeping.model.Person
import kotlinx.coroutines.launch

class PersonViewModel(
    val getPersonUseCase: GetPersonUseCase,
    val addPersonUseCase: AddPersonUseCase,
    val editPersonNameUseCase: EditPersonNameUseCase
) : ViewModel() {


    private val _nameLiveData = MutableLiveData("")
    val nameLiveData: LiveData<String> get() = _nameLiveData

    private var person: Person? = null

    fun loadData(personId: Long) {
        if (personId == -1L) return
        viewModelScope.launch {
            person = getPersonUseCase(personId)
            _nameLiveData.value = person!!.name
        }
    }

    fun changeName(name: String) {
        _nameLiveData.value = name
    }

    fun submit() {
        viewModelScope.launch {
            if (person == null) {
                addPersonUseCase(
                    Person(
                        id = System.currentTimeMillis(),
                        name = nameLiveData.value!!
                    )
                )
            } else {
                editPersonNameUseCase(
                    Person(
                        id = person!!.id,
                        name = nameLiveData.value!!
                    )
                )
            }
        }
    }
}