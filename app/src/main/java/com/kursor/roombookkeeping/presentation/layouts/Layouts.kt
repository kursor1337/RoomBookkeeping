package com.kursor.roombookkeeping.presentation.layouts

import com.kursor.roombookkeeping.presentation.layouts.Layouts.Args.PRICE_INDEX
import com.kursor.roombookkeeping.presentation.layouts.Layouts.Args.RECEIPT_ID

sealed class Layouts(val start: String, vararg args: String) {

    val route = start + args.joinToString(
        separator = "}/{",
        prefix = "/{",
        postfix = "}"
    )

    object SplashLayout : Layouts(start = "SplashLayout")

    object ReceiptListLayout : Layouts(start = "ReceiptListLayout")

    object ReceiptLayout : Layouts(
        start = "ReceiptLayout",
        RECEIPT_ID
    )

    object PriceLayout : Layouts(
        start = "PriceLayout",
        RECEIPT_ID, PRICE_INDEX
    )


    object Args {
        const val RECEIPT_ID = "receiptId"
        const val PRICE_INDEX = "priceIndex"
    }
}
