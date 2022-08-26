package com.kursor.roombookkeeping.presentation.layouts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.kursor.roombookkeeping.model.Receipt

@Composable
fun ReceiptListLayout(receiptList: List<com.kursor.roombookkeeping.model.Receipt>) {
    LazyColumn {
        items(receiptList) { receipt ->
            ReceiptLayout(receipt = receipt )
        }
    }
}