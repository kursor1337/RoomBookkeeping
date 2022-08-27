package com.kursor.roombookkeeping.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursor.roombookkeeping.domain.usecases.receipt.crud.GetReceiptListUseCase
import com.kursor.roombookkeeping.model.Receipt
import kotlinx.coroutines.launch

class ReceiptListViewModel(
    private val getReceiptListUseCase: GetReceiptListUseCase
) : ViewModel() {

    private val _receiptListLiveData = MutableLiveData<List<Receipt>>()
    val receiptListLiveData: LiveData<List<Receipt>> get() = _receiptListLiveData

    init {
        updateList()
    }

    fun updateList() {
        viewModelScope.launch {
            _receiptListLiveData.value = getReceiptListUseCase()!!
        }
    }

}