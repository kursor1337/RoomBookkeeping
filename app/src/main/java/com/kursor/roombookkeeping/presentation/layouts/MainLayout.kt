package com.kursor.roombookkeeping.presentation.layouts

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kursor.roombookkeeping.presentation.layouts.receipts.PriceLayout
import com.kursor.roombookkeeping.presentation.layouts.receipts.ReceiptLayout
import com.kursor.roombookkeeping.presentation.layouts.receipts.ReceiptListLayout
import com.kursor.roombookkeeping.presentation.layouts.receipts.SplashScreenLayout

@Composable
fun MainLayout() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Layouts.SplashLayout.route) {

        composable(Layouts.SplashLayout.route) {
            SplashScreenLayout()
        }

        composable(
            route = Layouts.ReceiptListLayout.start
        ) {
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

        composable(
            route = Layouts.PriceLayout.route,
            arguments = listOf(
                navArgument(Layouts.Args.RECEIPT_ID) { type = NavType.LongType },
                navArgument(Layouts.Args.PRICE_INDEX) { type = NavType.IntType }
            )
        ) {
            val receiptId = it.arguments!!.getLong(Layouts.Args.RECEIPT_ID)
            val priceIndex = it.arguments?.getInt(Layouts.Args.PRICE_INDEX) ?: -1
            PriceLayout(receiptId = receiptId, priceIndex = priceIndex)
        }
    }
}