package com.kursor.roombookkeeping.presentation.layouts

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kursor.roombookkeeping.presentation.layouts.receipts.ReceiptLayout
import com.kursor.roombookkeeping.presentation.layouts.receipts.ReceiptListLayout

@Composable
fun MainLayout() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Layouts.SplashLayout.route) {
        composable(route = Layouts.ReceiptListLayout.start) {
            ReceiptListLayout()
        }
        composable(
            route = Layouts.ReceiptLayout.start,
            arguments = listOf(
                navArgument(Layouts.Args.RECEIPT_ID) { type = NavType.LongType }
            )
        ) {
            val receiptId = it.arguments?.getLong(Layouts.Args.RECEIPT_ID) ?: -1
            ReceiptLayout(
                receiptId = receiptId
            )
        }
    }
}