package com.kursor.roombookkeeping.presentation.layouts.receipts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.kursor.roombookkeeping.viewModels.receipt.ReceiptListViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ReceiptListLayout(receiptListViewModel: ReceiptListViewModel = getViewModel()) {

    val receiptList = receiptListViewModel.receiptListLiveData.observeAsState(initial = emptyList())

    LazyColumn {
        items(receiptList.value) { receipt ->
            ReceiptListItemLayout(receipt = receipt)
        }
    }
}