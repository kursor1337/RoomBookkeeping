package com.kursor.roombookkeeping.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursor.roombookkeeping.domain.usecases.price.AddPersonToPriceUseCase
import com.kursor.roombookkeeping.domain.usecases.price.DeletePersonFromPriceUseCase
import com.kursor.roombookkeeping.domain.usecases.price.EditPriceUseCase
import com.kursor.roombookkeeping.domain.usecases.receipt.add.AddPriceToReceiptUseCase
import com.kursor.roombookkeeping.model.Person
import com.kursor.roombookkeeping.model.Price
import com.kursor.roombookkeeping.model.Receipt
import kotlinx.coroutines.launch

class PriceViewModel(
    val addPriceToReceiptUseCase: AddPriceToReceiptUseCase,
    val editPriceUseCase: EditPriceUseCase,
    val addPersonToPriceUseCase: AddPersonToPriceUseCase,
    val deletePersonFromPriceUseCase: DeletePersonFromPriceUseCase,
    val receipt: Receipt,
    val price: Price? = null
) : ViewModel() {

    private val _nameLiveData = MutableLiveData<String>()
    val nameLiveData: LiveData<String> get() = _nameLiveData

    private val _valueLiveData = MutableLiveData<Int>()
    val valueLiveData: LiveData<Int> get() = _valueLiveData

    private val _personsLiveData = MutableLiveData<List<Person>>()
    val personsLiveData: LiveData<List<Person>> get() = _personsLiveData

    init {
        _nameLiveData.value = price?.name ?: ""
        _valueLiveData.value = price?.value ?: 0
        _personsLiveData.value = price?.persons ?: mutableListOf()
    }

    fun changeName(newName: String) {
        _nameLiveData.value = newName
    }

    fun changeValue(newValue: String) {
        _valueLiveData.value = newValue.toInt()
    }

    fun addPerson(person: Person) {
        submit()
        viewModelScope.launch {
            addPersonToPriceUseCase(receipt, Price(
                name
            ))
        }
    }

    fun deletePerson(person: Person) {

    }

    fun submit() {
        viewModelScope.launch {
            if (price == null) {
                addPriceToReceiptUseCase(
                    receipt,
                    Price(
                        name = nameLiveData.value!!,
                        value = valueLiveData.value!!,
                        persons = personsLiveData.value!!
                    )
                )
            } else {
                val index = receipt.priceList.indexOf(price)
                if (index == -1) return@launch
                editPriceUseCase(
                    index, Price(
                        name = nameLiveData.value!!,
                        value = valueLiveData.value!!,
                        persons = personsLiveData.value!!
                    )
                )
            }
        }

    }

}