package com.kursor.roombookkeeping.presentation.layouts.receipts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.kursor.roombookkeeping.calculateCommonPersons
import com.kursor.roombookkeeping.viewModels.ReceiptViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ReceiptLayout(
    receiptId: Long,
    receiptViewModel: ReceiptViewModel = getViewModel()
) {

    val name = receiptViewModel.nameLiveData.observeAsState(initial = "")
    val priceList = receiptViewModel.priceListLiveData.observeAsState(initial = emptyList())

    if (receiptId != -1L) receiptViewModel.receiptId = receiptId

    Column {
        Text(text = name.value)
        Text(text = priceList.value.calculateCommonPersons().joinToString())
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


}