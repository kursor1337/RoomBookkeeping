package com.kursor.roombookkeeping.viewModels.price

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursor.roombookkeeping.domain.usecases.person.GetPersonListUseCase
import com.kursor.roombookkeeping.domain.usecases.price.AddPersonToPriceUseCase
import com.kursor.roombookkeeping.domain.usecases.price.DeletePersonFromPriceUseCase
import com.kursor.roombookkeeping.domain.usecases.price.EditPriceUseCase
import com.kursor.roombookkeeping.domain.usecases.receipt.AddPriceToReceiptUseCase
import com.kursor.roombookkeeping.domain.usecases.receipt.crud.GetReceiptUseCase
import com.kursor.roombookkeeping.model.Person
import com.kursor.roombookkeeping.model.Price
import com.kursor.roombookkeeping.model.Receipt
import kotlinx.coroutines.launch

class PriceViewModel(
    val addPriceToReceiptUseCase: AddPriceToReceiptUseCase,
    val editPriceUseCase: EditPriceUseCase,
    val getReceiptUseCase: GetReceiptUseCase,
    val getPersonListUseCase: GetPersonListUseCase
) : ViewModel() {

    private val _nameLiveData = MutableLiveData("")
    val nameLiveData: LiveData<String> get() = _nameLiveData

    private val _valueLiveData = MutableLiveData(0)
    val valueLiveData: LiveData<Int> get() = _valueLiveData

    private val _selectedPersonIndexesLiveData = MutableLiveData<List<Int>>(emptyList())
    val selectedPersonIndexesLiveData: LiveData<List<Int>> get() = _selectedPersonIndexesLiveData

    private val _wholePersonListLiveData = MutableLiveData<List<Person>>(emptyList())
    val wholePersonListLiveData: LiveData<List<Person>> get() = _wholePersonListLiveData

    lateinit var receipt: Receipt
    var price: Price? = null

    fun loadData(receiptId: Long, priceIndex: Int) {

        viewModelScope.launch {
            _wholePersonListLiveData.value = getPersonListUseCase()
            if (priceIndex == -1) return@launch

            receipt = getReceiptUseCase(receiptId!!)!!
            if (priceIndex == -1) return@launch
            price = receipt.priceList[priceIndex].also { price ->
                _valueLiveData.value = price.value
                _nameLiveData.value = price.name
                _selectedPersonIndexesLiveData.value =
                    wholePersonListLiveData.value!!.filter { person ->
                        person in price.persons
                    }.mapIndexed { index, person -> index }
            }
        }
    }

    fun changeName(newName: String) {
        _nameLiveData.value = newName
    }

    fun changeValue(newValue: String) {
        _valueLiveData.value = newValue.toInt()
    }

    fun changeSelectionForPerson(index: Int, checked: Boolean) {
        if (checked) {
            addPerson(index)
        } else deletePerson(index)
    }

    fun addPerson(index: Int) {
        _selectedPersonIndexesLiveData.value = _selectedPersonIndexesLiveData.value!!.plus(index)
    }

    fun deletePerson(index: Int) {
        _selectedPersonIndexesLiveData.value = _selectedPersonIndexesLiveData.value!!.minus(index)
    }

    fun submit() {
        if (!::receipt.isInitialized) return
        viewModelScope.launch {
            if (price == null) {
                addPriceToReceiptUseCase(
                    receipt = receipt,
                    price = Price(
                        name = nameLiveData.value!!,
                        value = valueLiveData.value!!,
                        persons = selectedPersonIndexesLiveData.value!!.map { personIndex ->
                            wholePersonListLiveData.value!![personIndex]
                        }
                    )
                )
            } else {
                val index = receipt.priceList.indexOf(price)
                if (index == -1) return@launch
                editPriceUseCase(
                    receipt = receipt,
                    index = index,
                    price = Price(
                        name = nameLiveData.value!!,
                        value = valueLiveData.value!!,
                        persons = selectedPersonIndexesLiveData.value!!.map { personIndex ->
                            wholePersonListLiveData.value!![personIndex]
                        }
                    )
                )
            }
        }

    }

}