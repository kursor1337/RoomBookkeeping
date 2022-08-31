package com.kursor.roombookkeeping.presentation.layouts

import com.kursor.roombookkeeping.presentation.layouts.Layouts.Args.PERSON_ID
import com.kursor.roombookkeeping.presentation.layouts.Layouts.Args.PRICE_INDEX
import com.kursor.roombookkeeping.presentation.layouts.Layouts.Args.RECEIPT_ID

sealed class Layouts(val start: String, vararg args: String) {

    val route = start + args.joinToString("") { "/{$it}" }

    object SplashLayout : Layouts(start = "SplashLayout")

    object ReceiptListLayout : Layouts(start = "ReceiptListLayout")

    object ReceiptLayout : Layouts(
        start = "ReceiptLayout",
        RECEIPT_ID
    ) {
        fun withArgs(receiptId: Long) = buildPath(receiptId.toString())
    }

    object PriceLayout : Layouts(
        start = "PriceLayout",
        RECEIPT_ID, PRICE_INDEX
    ) {
        fun withArgs(receiptId: Long, priceIndex: Int) =
            buildPath(receiptId.toString(), priceIndex.toString())
    }

    object PersonListLayout : Layouts(
        start = "PersonListLayout"
    )

    object PersonLayout : Layouts(
        start = "PersonLayout",
        PERSON_ID
    ) {
        fun withArgs(personId: Long) = buildPath(personId.toString())
    }


    object Args {
        const val RECEIPT_ID = "receiptId"
        const val PRICE_INDEX = "priceIndex"
        const val PERSON_ID = "personId"
    }

    protected fun buildPath(vararg args: String) = buildString {
        append(start)
        args.forEach {
            append("/$it")
        }
    }
}
