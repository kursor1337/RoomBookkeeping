package com.kursor.roombookkeeping.viewModels.receipt

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
import java.util.*

class ReceiptViewModel(
    val createReceiptUseCase: CreateReceiptUseCase,
    val updateReceiptUseCase: EditReceiptUseCase,
    val getReceiptUseCase: GetReceiptUseCase
) : ViewModel() {

    private val _priceListLiveData = MutableLiveData<List<Price>>(emptyList())
    val priceListLiveData: LiveData<List<Price>> get() = _priceListLiveData

    private val _nameLiveData = MutableLiveData("")
    val nameLiveData: LiveData<String> get() = _nameLiveData

    private var receipt: Receipt? = null

    fun loadData(receiptId: Long) {
        if (receiptId == -1L) return
        viewModelScope.launch {
            receipt = getReceiptUseCase(receiptId)!!
            _nameLiveData.value = receipt!!.name
            _priceListLiveData.value = receipt!!.priceList
        }
    }

    fun changeName(name: String) {
        _nameLiveData.value = name
    }

    fun deletePrice(price: Price) {
        viewModelScope.launch {
            _priceListLiveData.value = _priceListLiveData.value!!.minus(price)
        }

    }

    fun submit() {
        viewModelScope.launch {
            if (receipt == null) {
                val current = System.currentTimeMillis()
                createReceiptUseCase(
                    Receipt(
                        id = current,
                        name = nameLiveData.value!!,
                        dateTime = Date(current),
                        priceList = priceListLiveData.value!!
                    )
                )
            } else {
                updateReceiptUseCase(
                    Receipt(
                        id = receipt!!.id,
                        name = nameLiveData.value!!,
                        dateTime = receipt!!.dateTime,
                        priceList = priceListLiveData.value!!
                    )
                )
            }
        }
    }

}