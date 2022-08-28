package com.kursor.roombookkeeping.presentation.layouts.receipts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.kursor.roombookkeeping.R
import com.kursor.roombookkeeping.calculateCommonPersons
import com.kursor.roombookkeeping.viewModels.receipt.ReceiptViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ReceiptLayout(
    navController: NavController,
    receiptId: Long,
    receiptViewModel: ReceiptViewModel = getViewModel<ReceiptViewModel>().also {
        it.loadData(receiptId)
    }
) {

    val name = receiptViewModel.nameLiveData.observeAsState(initial = "")
    val priceList = receiptViewModel.priceListLiveData.observeAsState(initial = emptyList())

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

    Button(onClick = { receiptViewModel.submit() }) {
        Text(text = stringResource(id = R.string.submit))
    }


}