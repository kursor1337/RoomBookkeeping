package com.kursor.roombookkeeping.presentation.layouts.receipts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import com.kursor.roombookkeeping.viewModels.person.PersonListViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun PersonListLayout(personListViewModel: PersonListViewModel = getViewModel()) {

    val personList = personListViewModel.personListLiveData.observeAsState(initial = emptyList())
    
    personListViewModel.loadData()
    
    LazyColumn {
        itemsIndexed(personList.value) { index, person ->
            Text(
                text = person.name,
                modifier = Modifier.clickable {

                }
            )
        }
    }

}