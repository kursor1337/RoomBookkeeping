package com.kursor.roombookkeeping.model

import java.util.*

class Receipt(
    val name: String,
    val dateTime: Date,
    val priceList: List<Price>,
    val persons: List<Person>
) {
}