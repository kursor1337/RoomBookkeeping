package com.kursor.roombookkeeping.presentation.layouts.prices

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kursor.roombookkeeping.R
import com.kursor.roombookkeeping.presentation.layouts.Layouts
import com.kursor.roombookkeeping.presentation.special.SimpleTextField
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
    val selectedPersons =
        priceViewModel.selectedPersonIndexesLiveData.observeAsState(initial = emptyList())

    Log.i("PriceLayout", "start")



    Column {
        SimpleTextField(
            value = name.value,
            onValueChange = priceViewModel::changeName,
            placeholderText = stringResource(id = R.string.name_inanimate),
            modifier = Modifier.fillMaxWidth()
        )

        SimpleTextField(
            value = value.value.toString(),
            onValueChange = priceViewModel::changeValue,
            placeholderText = stringResource(id = R.string.price_value),
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn {
            itemsIndexed(wholePersonList.value) { index, person ->
                CheckboxRow(
                    text = person.name,
                    checked = index in selectedPersons.value,
                    onCheckedChange = {
                        priceViewModel.changeSelectionForPerson(
                            index = index,
                            checked = it
                        )
                    }
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            onClick = {
                navController.navigate(Layouts.PersonLayout.withArgs(-1))
            }) {
            Text(
                text = stringResource(id = R.string.create_new_person),
                maxLines = 2
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            onClick = {
                priceViewModel.submit()
                navController.popBackStack()
            }) {
            Text(text = stringResource(id = R.string.submit))
        }
    }
}

@Composable
fun CheckboxRow(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text(
            text = text,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}