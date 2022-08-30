package com.kursor.roombookkeeping.presentation.layouts.receipts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kursor.roombookkeeping.R
import com.kursor.roombookkeeping.calculateCommonPersons
import com.kursor.roombookkeeping.model.Person
import com.kursor.roombookkeeping.model.Price
import com.kursor.roombookkeeping.presentation.layouts.Layouts
import com.kursor.roombookkeeping.presentation.special.SimpleTextField
import com.kursor.roombookkeeping.viewModels.receipt.ReceiptViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ReceiptLayout(
    navController: NavController,
    scaffoldState: ScaffoldState,
    receiptId: Long,
    receiptViewModel: ReceiptViewModel = getViewModel<ReceiptViewModel>().also {
        it.loadData(receiptId)
    }
) {

    val name = receiptViewModel.nameLiveData.observeAsState(initial = "")
    val priceList = receiptViewModel.priceListLiveData.observeAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            Button(onClick = {
                receiptViewModel.submit()
                navController.navigate(
                    Layouts.PriceLayout.withArgs(
                        receiptId = if (receiptId == -1L) receiptViewModel.receipt!!.id else receiptId,
                        priceIndex = -1
                    )
                )
            }) {
                Text(text = stringResource(id = R.string.add_price))
            }
        }
    ) {
        Column {
            SimpleTextField(
                value = name.value,
                onValueChange = receiptViewModel::changeName,
                placeholderText = stringResource(id = R.string.name_inanimate)
            )
            Text(text = priceList.value.calculateCommonPersons().joinToString())
            LazyColumn {
                itemsIndexed(priceList.value) { index, price ->
                    PriceListItemLayout(
                        price = price,
                        modifier = Modifier.clickable {
                            navController.navigate(
                                Layouts.PriceLayout.withArgs(
                                    receiptId = receiptId,
                                    priceIndex = index
                                )
                            )
                        },
                        onDeletePriceButtonClick = {
                            receiptViewModel.deletePrice(price)
                        }
                    )
                }
            }
            Button(onClick = {
                receiptViewModel.submit()
                navController.popBackStack()
            }) {
                Text(text = stringResource(id = R.string.submit))
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


@Composable
fun PriceListItemLayout(
    price: Price,
    modifier: Modifier = Modifier,
    onDeletePriceButtonClick: (Price) -> Unit = { }
) {

    Row {
        Column(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(text = price.name)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = price.value.toString())
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = price.persons.joinToString())
        }
        IconButton(onClick = { onDeletePriceButtonClick(price) }) {

        }
    }
}

@Composable
fun PersonOutcomesLayout(personOutcomesMap: Map<Person, Double>) {
    LazyColumn {
        items(personOutcomesMap.toList()) { (person, outcome) ->
            Row {
                Text(text = person.name)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = String.format("%.2f", outcome))
            }
        }
    }
}