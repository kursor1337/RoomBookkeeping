package com.kursor.roombookkeeping.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursor.roombookkeeping.domain.usecases.receipt.AddPriceToReceiptUseCase
import com.kursor.roombookkeeping.domain.usecases.receipt.crud.GetReceiptUseCase
import com.kursor.roombookkeeping.domain.usecases.receipt.DeletePriceFromReceiptUseCase
import com.kursor.roombookkeeping.model.Price
import com.kursor.roombookkeeping.model.Receipt
import kotlinx.coroutines.launch

class ReceiptViewModel(
    val receiptId: Long,
    val getReceiptUseCase: GetReceiptUseCase,
    val addPriceToReceiptUseCase: AddPriceToReceiptUseCase,
    val deletePriceFromReceiptUseCase: DeletePriceFromReceiptUseCase
) : ViewModel() {

    private val _priceListLiveData = MutableLiveData<List<Price>>()
    val priceListLiveData: LiveData<List<Price>> get() = _priceListLiveData

    lateinit var receipt: Receipt

    init {
        updateReceipt()
    }

    fun updateReceipt() {
        viewModelScope.launch {
            receipt = getReceiptUseCase(receiptId)!!
        }
    }


    fun deletePrice(price: Price) {
        viewModelScope.launch {
            deletePriceFromReceiptUseCase(receipt, price)
        }

    }

    fun updatePriceList(index: Int) {
        viewModelScope.launch {
            updateReceipt()
            _priceListLiveData.value = receipt.priceList
        }
    }

}