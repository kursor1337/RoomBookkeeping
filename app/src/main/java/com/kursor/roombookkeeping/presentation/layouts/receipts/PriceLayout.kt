package com.kursor.roombookkeeping.presentation.layouts.receipts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kursor.roombookkeeping.model.Price
import com.kursor.roombookkeeping.model.Receipt
import com.kursor.roombookkeeping.viewModels.PriceViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PriceLayout(
    receiptId: Long,
    priceIndex: Int,
    priceViewModel: PriceViewModel = getViewModel()
) {

    if (priceIndex != -1) priceViewModel.receiptId = receiptId

    val name = priceViewModel.nameLiveData.observeAsState(initial = "")
    val value = priceViewModel.valueLiveData.observeAsState(initial = 0)
    val persons = priceViewModel.personsLiveData.observeAsState(initial = emptyList())



}