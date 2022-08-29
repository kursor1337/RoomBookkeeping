package com.kursor.roombookkeeping.data.database.typeConverters

import androidx.room.TypeConverter
import java.util.*

class DateTypeConverter {

    @TypeConverter
    fun fromDateToLong(date: Date): Long = date.time

    @TypeConverter
    fun fromLongToDate(time: Long): Date = Date(time)

}