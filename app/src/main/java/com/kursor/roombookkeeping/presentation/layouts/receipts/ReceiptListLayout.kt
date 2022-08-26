package com.kursor.roombookkeeping.presentation.layouts.receipts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

@Composable
fun ReceiptListLayout(receiptList: List<com.kursor.roombookkeeping.model.Receipt>) {
    LazyColumn {
        items(receiptList) { receipt ->
            ReceiptLayout(receipt = receipt )
        }
    }
}