package com.kursor.roombookkeeping.presentation.layouts.receipts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kursor.roombookkeeping.R
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
    val selectedReceipts =
        receiptListViewModel.selectedReceiptsLiveData.observeAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            Button(onClick = {
                navController.navigate(Layouts.ReceiptLayout.withArgs(-1))
            }) {
                Text(text = stringResource(id = R.string.add_receipt))
            }
        },
        topBar = {
            TopAppBar {
                Spacer(modifier = Modifier.weight(1f, fill = true))
                if (selectedReceipts.value.isNotEmpty()) {
                    IconButton(onClick = { receiptListViewModel.deleteSelectedReceipts() }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "DeleteReceipts"
                        )
                    }
                }
                IconButton(
                    onClick = {
                        navController.navigate(Layouts.PersonListLayout.route)
                    }) {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = "PersonList")
                }
            }
        }
    ) {
        LazyColumn {
            items(receiptList.value) { receipt ->
                SelectableReceiptListItemLayout(
                    receipt = receipt,
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    receiptListViewModel.changeSelectionForReceipt(receipt)
                                },
                                onTap = {
                                    if (selectedReceipts.value.isEmpty())
                                        navController.navigate(
                                            Layouts.ReceiptLayout.withArgs(
                                                receipt.id
                                            )
                                        )
                                    else receiptListViewModel.changeSelectionForReceipt(receipt)
                                }
                            )
                        },
                    selectionCriteria = { receipt in selectedReceipts.value }
                )
            }
        }
    }
}

@Composable
fun SelectableReceiptListItemLayout(
    receipt: Receipt,
    modifier: Modifier = Modifier,
    selectionCriteria: () -> Boolean
) {
    if (selectionCriteria()) SelectedReceiptListItemLayout(receipt = receipt, modifier = modifier)
    else UnselectedReceiptListItemLayout(receipt = receipt, modifier = modifier)
}


@Composable
fun UnselectedReceiptListItemLayout(
    receipt: Receipt,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Unspecified
) {
    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = receipt.name,
                style = TextStyle(
                    fontSize = 22.sp
                ),
                color = textColor
            )
            Text(
                text = receipt.priceList.sumOf { it.value }.toString(),
                color = textColor
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .padding(
                    horizontal = 12.dp,
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
                color = textColor,
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
                color = textColor,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun SelectedReceiptListItemLayout(
    receipt: Receipt,
    modifier: Modifier = Modifier
) {
    Surface(color = MaterialTheme.colors.secondary) {
        UnselectedReceiptListItemLayout(
            receipt = receipt,
            modifier = modifier,
            textColor = MaterialTheme.colors.primary
        )
    }
}