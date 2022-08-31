package com.kursor.roombookkeeping.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kursor.roombookkeeping.model.Price
import java.util.*

@Entity
data class ReceiptEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo val name: String,
    @ColumnInfo val dateTime: Date,
    @ColumnInfo val priceList: List<Price>
)