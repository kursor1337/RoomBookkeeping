package com.kursor.roombookkeeping.viewModels.receipt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursor.roombookkeeping.domain.usecases.receipt.crud.DeleteReceiptUseCase
import com.kursor.roombookkeeping.domain.usecases.receipt.crud.GetReceiptListUseCase
import com.kursor.roombookkeeping.model.Receipt
import kotlinx.coroutines.launch

class ReceiptListViewModel(
    private val getReceiptListUseCase: GetReceiptListUseCase,
    private val deleteReceiptUseCase: DeleteReceiptUseCase
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

    fun deleteReceipt(index: Int) {
        viewModelScope.launch {
            deleteReceiptUseCase(_receiptListLiveData.value!![index])
            _receiptListLiveData.value = getReceiptListUseCase()
        }
    }

}