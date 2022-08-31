package com.kursor.roombookkeeping.presentation.layouts.receipts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kursor.roombookkeeping.R
import com.kursor.roombookkeeping.calculateCommonPersons
import com.kursor.roombookkeeping.model.Person
import com.kursor.roombookkeeping.model.Price
import com.kursor.roombookkeeping.presentation.layouts.Layouts
import com.kursor.roombookkeeping.presentation.special.RoomBookkeepingTopAppBar
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
    val outcomes = receiptViewModel.outcomesLiveData.observeAsState(initial = emptyMap())

    receiptViewModel.updateData(receiptId)

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
        },
        topBar = { RoomBookkeepingTopAppBar(navController = navController) }
    ) {
        LazyColumn {
            item {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = receiptViewModel::changeName,
                    placeholder = {
                        Text(text = stringResource(id = R.string.name_inanimate))
                    },
                    modifier = Modifier
                        .padding(4.dp)
                )
                if (priceList.value.isEmpty()) {
                    Text(
                        text = if (priceList.value.isEmpty()) "No prices?"
                        else "Between: " + priceList.value
                            .calculateCommonPersons().joinToString {
                                it.name
                            },
                        modifier = Modifier.padding(
                            vertical = 4.dp,
                            horizontal = 8.dp
                        )
                    )
                }
            }
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

            item {
                Button(onClick = {
                    receiptViewModel.submit()
                    navController.popBackStack()
                }) {
                    Text(text = stringResource(id = R.string.submit))
                }

                Spacer(modifier = Modifier.height(12.dp))
                PersonOutcomesLayout(
                    personOutcomesMap = outcomes.value,
                    modifier = Modifier
                        .padding(12.dp)
                )

            }
        }

    }
}


@Composable
fun PriceListItemLayout(
    price: Price,
    modifier: Modifier = Modifier,
    onDeletePriceButtonClick: (Price) -> Unit = { }
) {

    Surface(
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colors.surface,
        modifier = Modifier.padding(2.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                )
        ) {
            Column {
                Text(text = price.name)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = price.value.toString())
            }
            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = stringResource(id = R.string._for) + ": " + price.persons.joinToString { it.name },
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(4.dp)
            )

            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { onDeletePriceButtonClick(price) },
                modifier = Modifier.fillMaxHeight()
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete, contentDescription = "DeletePrice",
                    modifier = Modifier.fillMaxHeight(0.5f)
                )
            }
        }
    }

}

@Composable
fun PersonOutcomesLayout(
    personOutcomesMap: Map<Person, Double>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        for ((person, outcome) in personOutcomesMap) {
            Row {
                Text(text = person.name)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = String.format("%.2f", outcome))
            }
        }
    }
}