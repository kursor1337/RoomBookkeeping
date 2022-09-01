package com.kursor.roombookkeeping.viewModels.price

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursor.roombookkeeping.domain.usecases.person.GetPersonListUseCase
import com.kursor.roombookkeeping.domain.usecases.price.EditPriceUseCase
import com.kursor.roombookkeeping.domain.usecases.receipt.AddPriceToReceiptUseCase
import com.kursor.roombookkeeping.domain.usecases.receipt.crud.GetReceiptUseCase
import com.kursor.roombookkeeping.model.Person
import com.kursor.roombookkeeping.model.Price
import com.kursor.roombookkeeping.model.Receipt
import com.kursor.roombookkeeping.viewModels.ReceiptViewModelBuffer
import kotlinx.coroutines.launch
import java.lang.Exception

class PriceViewModel(
    val addPriceToReceiptUseCase: AddPriceToReceiptUseCase,
    val editPriceUseCase: EditPriceUseCase,
    val getReceiptUseCase: GetReceiptUseCase,
    val getPersonListUseCase: GetPersonListUseCase
) : ViewModel() {

    private val _nameLiveData = MutableLiveData("")
    val nameLiveData: LiveData<String> get() = _nameLiveData

    private val _valueLiveData = MutableLiveData("")
    val valueLiveData: LiveData<String> get() = _valueLiveData

    private val _selectedPersonIndexesLiveData = MutableLiveData<List<Int>>(emptyList())
    val selectedPersonIndexesLiveData: LiveData<List<Int>> get() = _selectedPersonIndexesLiveData

    private val _wholePersonListLiveData = MutableLiveData<List<Person>>(emptyList())
    val wholePersonListLiveData: LiveData<List<Person>> get() = _wholePersonListLiveData

    private var receipt: Receipt? = null
    private var price: Price? = null

    lateinit var mode: Mode

    fun loadData(receiptId: Long, priceIndex: Int) {

        mode = getMode(receiptId, priceIndex)

        viewModelScope.launch {
            _wholePersonListLiveData.value = getPersonListUseCase()

            when (mode) {
                Mode.ADD_NEW_TO_NEW -> {
                    _selectedPersonIndexesLiveData.value =
                        wholePersonListLiveData.value!!.mapIndexed { index, person -> index }
                }
                Mode.ADD_NEW_TO_OLD -> {
                    receipt = getReceiptUseCase(receiptId)
                    _selectedPersonIndexesLiveData.value =
                        wholePersonListLiveData.value!!.mapIndexed { index, person -> index }
                }
                Mode.EDIT_OLD_ON_NEW -> {
                    price = ReceiptViewModelBuffer.priceList[priceIndex]
                    _selectedPersonIndexesLiveData.value =
                        wholePersonListLiveData.value!!.filter { person ->
                            person in price!!.persons
                        }.mapIndexed { index, person -> index }
                }
                Mode.EDIT_OLD_ON_OLD -> {
                    receipt = getReceiptUseCase(receiptId)
                    price = receipt!!.priceList[priceIndex].also { price ->
                        _valueLiveData.value = price.value.toString()
                        _nameLiveData.value = price.name
                        _selectedPersonIndexesLiveData.value =
                            wholePersonListLiveData.value!!.filter { person ->
                                person in price.persons
                            }.mapIndexed { index, person -> index }
                    }
                    _selectedPersonIndexesLiveData.value =
                        wholePersonListLiveData.value!!.filter { person ->
                            person in price!!.persons
                        }.mapIndexed { index, person -> index }
                }
            }
        }
    }

    fun changeName(newName: String) {
        _nameLiveData.value = newName
    }

    fun changeValue(newValue: String) {
        _valueLiveData.value = newValue
    }

    fun changeSelectionForPerson(index: Int, checked: Boolean) {
        if (checked) {
            addPerson(index)
        } else deletePerson(index)
    }

    private fun addPerson(index: Int) {
        _selectedPersonIndexesLiveData.value = _selectedPersonIndexesLiveData.value!!.plus(index)
    }

    private fun deletePerson(index: Int) {
        _selectedPersonIndexesLiveData.value = _selectedPersonIndexesLiveData.value!!.minus(index)
    }

    fun submit(): Boolean {
        try {
            valueLiveData.value!!.toInt()
        } catch (e: Exception) {
            return false
        }
        viewModelScope.launch {
            when (mode) {
                Mode.ADD_NEW_TO_NEW -> {
                    ReceiptViewModelBuffer.priceList.add(
                        Price(
                            name = nameLiveData.value!!,
                            value = valueLiveData.value!!.toInt(),
                            persons = selectedPersonIndexesLiveData.value!!.map { personIndex ->
                                wholePersonListLiveData.value!![personIndex]
                            }
                        )
                    )
                }
                Mode.ADD_NEW_TO_OLD -> {
                    addPriceToReceiptUseCase(
                        receipt = receipt!!,
                        price = Price(
                            name = nameLiveData.value!!,
                            value = valueLiveData.value!!.toInt(),
                            persons = selectedPersonIndexesLiveData.value!!.map { personIndex ->
                                wholePersonListLiveData.value!![personIndex]
                            }
                        )
                    )
                }
                Mode.EDIT_OLD_ON_NEW -> {
                    val index = ReceiptViewModelBuffer.priceList.indexOf(price!!)
                    ReceiptViewModelBuffer.priceList.set(
                        index = index,
                        element = Price(
                            name = nameLiveData.value!!,
                            value = valueLiveData.value!!.toInt(),
                            persons = selectedPersonIndexesLiveData.value!!.map { personIndex ->
                                wholePersonListLiveData.value!![personIndex]
                            }
                        )
                    )
                }
                Mode.EDIT_OLD_ON_OLD -> {
                    val index = receipt!!.priceList.indexOf(price)
                    editPriceUseCase(
                        receipt = receipt!!,
                        index = index,
                        price = Price(
                            name = nameLiveData.value!!,
                            value = valueLiveData.value!!.toInt(),
                            persons = selectedPersonIndexesLiveData.value!!.map { personIndex ->
                                wholePersonListLiveData.value!![personIndex]
                            }
                        )
                    )
                }
            }
        }
        return true
    }

    private fun getMode(receiptId: Long, priceIndex: Int): Mode {
        return when {
            receiptId == -1L && priceIndex == -1 -> Mode.ADD_NEW_TO_NEW
            receiptId == -1L && priceIndex != -1 -> Mode.EDIT_OLD_ON_NEW
            receiptId != -1L && priceIndex == -1 -> Mode.ADD_NEW_TO_OLD
            receiptId != -1L && priceIndex != -1 -> Mode.EDIT_OLD_ON_OLD
            else -> Mode.ADD_NEW_TO_NEW
        }
    }

    enum class Mode {
        ADD_NEW_TO_NEW, ADD_NEW_TO_OLD, EDIT_OLD_ON_NEW, EDIT_OLD_ON_OLD
    }

}