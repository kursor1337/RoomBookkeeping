package com.kursor.roombookkeeping.data.database.entities

import androidx.room.Entity
import com.kursor.roombookkeeping.model.Person

@Entity
data class PriceEntity(
    val name: String,
    val value: Int,
    val personIds: List<Long>
) {
}