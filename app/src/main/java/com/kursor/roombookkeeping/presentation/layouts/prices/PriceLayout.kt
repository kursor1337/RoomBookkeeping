package com.kursor.roombookkeeping.presentation.layouts.prices

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.kursor.roombookkeeping.R
import com.kursor.roombookkeeping.presentation.layouts.Layouts
import com.kursor.roombookkeeping.viewModels.price.PriceViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun PriceLayout(
    navController: NavController,
    scaffoldState: ScaffoldState,
    receiptId: Long,
    priceIndex: Int,
    priceViewModel: PriceViewModel = getViewModel<PriceViewModel>().also {
        it.loadData(receiptId, priceIndex)
    }
) {

    val name = priceViewModel.nameLiveData.observeAsState(initial = "")
    val value = priceViewModel.valueLiveData.observeAsState(initial = 0)
    val wholePersonList =
        priceViewModel.wholePersonListLiveData.observeAsState(initial = emptyList())

    Row {
        Column {
            TextField(
                value = name.value,
                onValueChange = priceViewModel::changeName,
                placeholder = { Text(text = stringResource(id = R.string.name_inanimate)) }
            )

            TextField(
                value = value.value.toString(),
                onValueChange = priceViewModel::changeValue,
                placeholder = { Text(text = stringResource(id = R.string.price_value)) }
            )
        }

        LazyColumn {
            itemsIndexed(wholePersonList.value) { index, person ->
                if (index != wholePersonList.value.lastIndex) {
                    Button(onClick = {
                        navController.navigate(Layouts.PersonLayout.withArgs(-1))
                    }) {
                        Text(text = stringResource(id = R.string.create_new_person))
                    }
                } else
                    CheckboxRow(
                        text = person.name,
                        onCheckedChange = {
                            priceViewModel.changeSelectionForPerson(
                                index = index,
                                checked = it
                            )
                        }
                    )
            }
        }

        Button(onClick = { priceViewModel.submit() }) {
            Text(text = stringResource(id = R.string.submit))
            navController.popBackStack()
        }
        
        DisposableEffect(key1 = true) {
            onDispose {
                priceViewModel.submit()
            }
        }
    }
}

@Composable
fun CheckboxRow(
    text: String,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row {
        Checkbox(checked = true, onCheckedChange = onCheckedChange)
        Text(text = text)
    }
}