package com.kursor.roombookkeeping.presentation.layouts.persons

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import com.kursor.roombookkeeping.R
import com.kursor.roombookkeeping.viewModels.person.PersonViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun PersonLayout(
    personId: Long,
    personViewModel: PersonViewModel = getViewModel()
) {

    val name = personViewModel.nameLiveData.observeAsState(initial = "")

    personViewModel.loadData(personId)

    TextField(value = name.value, onValueChange = personViewModel::changeName)

    Button(onClick = { personViewModel.submit() }) {
        Text(text = stringResource(id = R.string.submit))
    }
}