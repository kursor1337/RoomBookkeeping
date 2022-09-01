package com.kursor.roombookkeeping.presentation.layouts.prices

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kursor.roombookkeeping.R
import com.kursor.roombookkeeping.presentation.layouts.Layouts
import com.kursor.roombookkeeping.presentation.special.RoomBookkeepingTopAppBar
import com.kursor.roombookkeeping.viewModels.price.PriceViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun PriceLayout(
    navController: NavController,
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

    val context = LocalContext.current

    Scaffold(
        topBar = { RoomBookkeepingTopAppBar(navController = navController) }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            OutlinedTextField(
                value = name.value,
                onValueChange = priceViewModel::changeName,
                placeholder = {
                    Text(text = stringResource(id = R.string.name_inanimate))
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = value.value.toString(),
                onValueChange = priceViewModel::changeValue,
                placeholder = {
                    Text(text = stringResource(id = R.string.price_value))
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
                    val success = priceViewModel.submit()
                    if (success) navController.popBackStack()
                    else {
                        Toast.makeText(
                            context,
                            R.string.only_numbers,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }) {
                Text(text = stringResource(id = R.string.submit))
            }
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
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.primary,
                uncheckedColor = Color.White,
                checkmarkColor = Color.White
            )
        )
        Text(
            text = text,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}