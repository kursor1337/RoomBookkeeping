package com.kursor.roombookkeeping.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursor.roombookkeeping.domain.usecases.receipt.crud.GetReceiptUseCase
import com.kursor.roombookkeeping.domain.usecases.receipt.crud.CreateReceiptUseCase
import com.kursor.roombookkeeping.domain.usecases.receipt.crud.EditReceiptUseCase
import com.kursor.roombookkeeping.model.Price
import com.kursor.roombookkeeping.model.Receipt
import kotlinx.coroutines.launch

class ReceiptViewModel(
    val createReceiptUseCase: CreateReceiptUseCase,
    val updateReceiptUseCase: EditReceiptUseCase,
    val getReceiptUseCase: GetReceiptUseCase
) : ViewModel() {

    private val _priceListLiveData = MutableLiveData<List<Price>>()
    val priceListLiveData: LiveData<List<Price>> get() = _priceListLiveData

    private val _nameLiveData = MutableLiveData<String>()
    val nameLiveData: LiveData<String> get() = _nameLiveData

    var receiptId: Int? = null
        set(value) {
            field = value
            if (value != null) {
                getReceiptData()
            }
        }

    var receipt: Receipt? = null

    private fun getReceiptData() {
        viewModelScope.launch {
            val id = receipt?.id ?: return@launch
            receipt = getReceiptUseCase(id)!!
            _nameLiveData.value = receipt!!.name
            _priceListLiveData.value = receipt!!.priceList
        }
    }


    fun deletePrice(price: Price) {
        viewModelScope.launch {
            _priceListLiveData.value = _priceListLiveData.value!!.minus(price)
        }

    }

    fun submit() {
        if (receiptId == null) return
        viewModelScope.launch {
            if (receipt == null) {
                createReceiptUseCase(receipt!!)
            }
        }
    }

}