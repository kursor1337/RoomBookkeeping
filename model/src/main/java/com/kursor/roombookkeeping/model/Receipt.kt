package com.kursor.roombookkeeping.model

import java.util.*

class Receipt(
    val id: Long,
    val name: String,
    val dateTime: Date,
    val priceList: List<Price>,
) {

    val persons: List<Price>
    get() {
        val result = mutableListOf<Person>()
        priceList.
    }

}