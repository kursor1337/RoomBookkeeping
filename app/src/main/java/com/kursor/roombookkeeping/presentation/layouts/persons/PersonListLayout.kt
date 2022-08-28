package com.kursor.roombookkeeping.presentation.layouts.persons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.NavController
import com.kursor.roombookkeeping.presentation.layouts.Layouts
import com.kursor.roombookkeeping.viewModels.person.PersonListViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun PersonListLayout(
    navController: NavController,
    personListViewModel: PersonListViewModel = getViewModel<PersonListViewModel>().also {
        it.loadData()
    },
) {

    val personList = personListViewModel.personListLiveData.observeAsState(initial = emptyList())

    LazyColumn {
        itemsIndexed(personList.value) { index, person ->
            Text(
                text = person.name,
                modifier = Modifier.clickable {
                    navController.navigate(Layouts.PersonLayout.route)
                }
            )
        }
    }

}