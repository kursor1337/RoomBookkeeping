package com.kursor.roombookkeeping.presentation.layouts.receipts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kursor.roombookkeeping.model.Receipt
import com.kursor.roombookkeeping.presentation.layouts.Layouts
import com.kursor.roombookkeeping.viewModels.receipt.ReceiptListViewModel
import org.koin.androidx.compose.getViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ReceiptListLayout(
    navController: NavController,
    scaffoldState: ScaffoldState,
    receiptListViewModel: ReceiptListViewModel = getViewModel<ReceiptListViewModel>().also {
        it.loadData()
    }
) {

    val receiptList = receiptListViewModel.receiptListLiveData.observeAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Layouts.ReceiptLayout.withArgs(-1))
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "AddReceipt")
            }
        }
    ) {
        LazyColumn {
            items(receiptList.value) { receipt ->
                ReceiptListItemLayout(
                    receipt = receipt,
                    modifier = Modifier.clickable {
                        navController.navigate(Layouts.ReceiptLayout.withArgs(receipt.id))
                    }
                )
            }
        }
    }
}

@Composable
fun ReceiptListItemLayout(
    receipt: Receipt,
    modifier: Modifier = Modifier
) {

    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .width(150.dp)
        ) {
            Text(
                text = receipt.name,
                style = TextStyle(
                    fontSize = 22.sp
                )
            )
            Text(text = receipt.priceList.sumOf { it.value }.toString())
        }
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                )
        ) {
            Text(
                text = SimpleDateFormat(
                    "hh:mm:ss",
                    Locale.getDefault()
                ).format(receipt.dateTime),
                style = TextStyle(
                    fontSize = 16.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = SimpleDateFormat(
                    "dd.MM.yyyy",
                    Locale.getDefault()
                ).format(receipt.dateTime),
                style = TextStyle(
                    fontSize = 16.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
            )
        }

    }

}