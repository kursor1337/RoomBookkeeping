package com.kursor.roombookkeeping.presentation.special.dontGoHere

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.kursor.roombookkeeping.R
import kotlinx.coroutines.delay

@Composable
fun NothingImportantLayout(
    navController: NavController
) {
    var andreyAlphaState by remember {
        mutableStateOf(0f)
    }
    var shveinAlphaState by remember {
        mutableStateOf(0f)
    }

    LaunchedEffect(key1 = Unit) {
        for (i in 0..1000) {
            delay(3)
            andreyAlphaState += 0.001f
        }
        delay(200)
        for (i in 0..1000) {
            delay(3)
            andreyAlphaState -= 0.001f
        }
        for (i in 0..1000) {
            delay(3)
            shveinAlphaState += 0.001f
        }
        delay(200)
        for (i in 0..1000) {
            delay(3)
            shveinAlphaState -= 0.001f
        }
        navController.popBackStack()
    }

    Andrey(alpha = andreyAlphaState)
    Shvein(alpha = shveinAlphaState)
}

@Composable
fun Andrey(alpha: Float) {
    Image(
        painter = painterResource(id = R.drawable.andrey),
        contentDescription = "",
        modifier = Modifier
            .fillMaxSize()
            .alpha(alpha)
    )
}

@Composable
fun Shvein(alpha: Float) {
    Image(
        painter = painterResource(id = R.drawable.shvein),
        contentDescription = "",
        modifier = Modifier
            .fillMaxSize()
            .alpha(alpha)
    )
}