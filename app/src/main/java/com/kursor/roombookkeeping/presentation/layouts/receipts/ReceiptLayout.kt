package com.kursor.roombookkeeping.presentation.layouts.receipts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.kursor.roombookkeeping.model.Receipt
import com.kursor.roombookkeeping.viewModels.ReceiptViewModel
import org.koin.androidx.compose.viewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ReceiptLayout(receipt: Receipt) {

    val receiptViewModel by viewModel<ReceiptViewModel> { parametersOf(receipt) }

    val name = receiptViewModel.nameLiveData.observeAsState(initial = "")
    val priceList = receiptViewModel.priceListLiveData.observeAsState(initial = emptyList())

    LazyColumn {
        items(priceList.value) { price ->
            Column {
                Text(text = price.name)
                Text(text = price.value.toString())
                Text(text = price.persons.joinToString())
            }
        }
    }

}