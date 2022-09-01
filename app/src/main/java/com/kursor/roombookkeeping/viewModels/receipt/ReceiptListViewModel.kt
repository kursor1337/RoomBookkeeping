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

    private val _receiptListLiveData = MutableLiveData<List<Receipt>>(emptyList())
    val receiptListLiveData: LiveData<List<Receipt>> get() = _receiptListLiveData

    private val _selectedReceiptsLiveData = MutableLiveData<List<Receipt>>(emptyList())
    val selectedReceiptsLiveData: LiveData<List<Receipt>> get() = _selectedReceiptsLiveData

    fun loadData() {
        viewModelScope.launch {
            _receiptListLiveData.value = getReceiptListUseCase()
        }
    }

    private fun selectReceipt(receipt: Receipt) {
        _selectedReceiptsLiveData.value = _selectedReceiptsLiveData.value?.plus(receipt)
    }

    private fun unselectReceipt(receipt: Receipt) {
        _selectedReceiptsLiveData.value = _selectedReceiptsLiveData.value?.minus(receipt)
    }

    fun changeSelectionForReceipt(receipt: Receipt) {
        if (receipt in _selectedReceiptsLiveData.value!!) {
            unselectReceipt(receipt = receipt)
        } else selectReceipt(receipt = receipt)
    }

    fun deleteReceipt(index: Int) {
        viewModelScope.launch {
            deleteReceiptUseCase(_receiptListLiveData.value!![index])
            _receiptListLiveData.value = getReceiptListUseCase()
        }
    }

    fun deleteSelectedReceipts() {
        viewModelScope.launch {
            selectedReceiptsLiveData.value!!.forEach {
                deleteReceiptUseCase(receipt = it)
            }
            _selectedReceiptsLiveData.value = emptyList()
            _receiptListLiveData.value = getReceiptListUseCase()
        }
    }

}