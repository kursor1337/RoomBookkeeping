package com.kursor.roombookkeeping.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursor.roombookkeeping.domain.usecases.receipt.GetReceiptUseCase
import com.kursor.roombookkeeping.domain.usecases.receipt.UpdateReceiptUseCase
import com.kursor.roombookkeeping.model.Person
import com.kursor.roombookkeeping.model.Price
import com.kursor.roombookkeeping.model.Receipt
import kotlinx.coroutines.launch

class PriceListViewModel(
    val receiptId: Long,
    val updateReceiptUseCase: UpdateReceiptUseCase,
    val getReceiptUseCase: GetReceiptUseCase,
) : ViewModel() {

    private val _priceListLiveData = MutableLiveData<List<Price>>()
    val priceListLiveData: LiveData<List<Price>> get() = _priceListLiveData


    fun updatePrice(index: Int, price: Price) {
        viewModelScope.launch {
            val receipt = getReceiptUseCase(receiptId) ?: return@launch
            val updatedReceipt = Receipt(
                id = receipt.id,
                name = receipt.name,
                dateTime = receipt.dateTime,
                priceList = receipt.priceList.toMutableList().apply {
                    this[index] = price
                },
                persons = receipt.persons
            )
        }
    }

    fun addPrice(price: Price) {

    }

    fun addPersonToPrice(person: Person) {

    }

    fun deletePrice(price: Price) {

    }

    fun deletePersonFromPrice()

    fun updatePriceList(index: Int) {
        viewModelScope.launch {
            _priceListLiveData.value = getReceiptUseCase(receiptId)?.priceList ?: emptyList()
        }
    }

}