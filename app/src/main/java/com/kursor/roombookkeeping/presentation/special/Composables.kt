package com.kursor.roombookkeeping.presentation.special

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kursor.roombookkeeping.R


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
        Spacer(
            modifier = Modifier
                .weight(1f, fill = true)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            navController.navigate("nothingImportantHere")
                        }
                    )
                }
        )
        rightSideBlock()

    }
}

@Composable
fun ListItemLayout(
    index: Int,
    modifier: Modifier = Modifier,
    block: @Composable () -> Unit
) {
    if (index == 0) Divider()
    block()
    Divider(thickness = 2.dp)
}