package com.kursor.roombookkeeping.presentation.layouts.receipts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.kursor.roombookkeeping.model.Price

@Composable
fun PriceListLayout() {



    LazyColumn {
        items(priceList) { price ->

        }
    }
}