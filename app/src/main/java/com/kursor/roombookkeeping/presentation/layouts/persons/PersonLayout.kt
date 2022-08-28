package com.kursor.roombookkeeping.presentation.layouts.persons

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.kursor.roombookkeeping.R
import com.kursor.roombookkeeping.viewModels.person.PersonViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun PersonLayout(
    navController: NavController,
    personId: Long,
    personViewModel: PersonViewModel = getViewModel<PersonViewModel>().also {
        it.loadData(personId)
    }
) {

    val name = personViewModel.nameLiveData.observeAsState(initial = "")

    TextField(value = name.value, onValueChange = personViewModel::changeName)

    Button(onClick = { personViewModel.submit() }) {
        Text(text = stringResource(id = R.string.submit))
    }
}