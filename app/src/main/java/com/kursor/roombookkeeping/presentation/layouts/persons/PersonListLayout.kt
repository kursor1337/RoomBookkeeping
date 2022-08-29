package com.kursor.roombookkeeping.presentation.layouts.persons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.kursor.roombookkeeping.presentation.layouts.Layouts
import com.kursor.roombookkeeping.viewModels.person.PersonListViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun PersonListLayout(
    navController: NavController,
    scaffoldState: ScaffoldState,
    personListViewModel: PersonListViewModel = getViewModel<PersonListViewModel>().also {
        it.loadData()
    },
) {

    val personList = personListViewModel.personListLiveData.observeAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { 
                navController.navigate(Layouts.PersonLayout.withArgs(-1))
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Person")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
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
}