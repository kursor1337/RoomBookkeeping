package com.kursor.roombookkeeping.presentation.special

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kursor.roombookkeeping.R
import com.kursor.roombookkeeping.presentation.layouts.Layouts


@Composable
fun RoomBookkeepingTopAppBar(
    navController: NavController,
    rightSideBlock: @Composable () -> Unit = { }
) {

    TopAppBar {
        Text(
            text = stringResource(id = R.string.app_name),
            style = TextStyle(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Spacer(modifier = Modifier.weight(1f, fill = true))
        rightSideBlock()

    }
}