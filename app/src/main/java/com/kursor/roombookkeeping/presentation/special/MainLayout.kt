package com.kursor.roombookkeeping.presentation.layouts

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kursor.roombookkeeping.presentation.layouts.persons.PersonLayout
import com.kursor.roombookkeeping.presentation.layouts.persons.PersonListLayout
import com.kursor.roombookkeeping.presentation.layouts.prices.PriceLayout
import com.kursor.roombookkeeping.presentation.layouts.receipts.ReceiptLayout
import com.kursor.roombookkeeping.presentation.layouts.receipts.ReceiptListLayout

@Composable
fun MainLayout() {

    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    NavHost(navController = navController, startDestination = Layouts.ReceiptListLayout.route) {

        composable(Layouts.SplashLayout.route) {
            SplashScreenLayout()
        }

        composable(
            route = Layouts.ReceiptListLayout.route
        ) {
            ReceiptListLayout(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }

        composable(
            route = Layouts.ReceiptLayout.route,
            arguments = listOf(
                navArgument(Layouts.Args.RECEIPT_ID) { type = NavType.LongType }
            )
        ) {
            val receiptId = it.arguments?.getLong(Layouts.Args.RECEIPT_ID) ?: -1
            ReceiptLayout(
                navController = navController,
                scaffoldState = scaffoldState,
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
            PriceLayout(
                navController = navController,
                scaffoldState = scaffoldState,
                receiptId = receiptId,
                priceIndex = priceIndex
            )
        }

        composable(
            route = Layouts.PersonListLayout.route
        ) {
            PersonListLayout(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }

        composable(
            route = Layouts.PersonLayout.route,
            arguments = listOf(
                navArgument(Layouts.Args.PERSON_ID) { type = NavType.LongType }
            )
        ) {
            val personId = it.arguments?.getLong(Layouts.Args.PERSON_ID) ?: -1
            PersonLayout(
                navController = navController,
                scaffoldState = scaffoldState,
                personId = personId
            )
        }

    }
}