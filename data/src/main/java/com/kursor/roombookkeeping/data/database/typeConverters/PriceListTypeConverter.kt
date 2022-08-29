package com.kursor.roombookkeeping.data.database.typeConverters

import androidx.room.TypeConverter
import com.kursor.roombookkeeping.model.Price
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types

class PriceListTypeConverter {

    val priceListType = Types.newParameterizedType(List::class.java, Price::class.java)

    val adapter: JsonAdapter<List<Price>> = moshi.adapter(priceListType)

    @TypeConverter
    fun fromPriceToString(priceList: List<Price>): String = adapter.toJson(priceList)

    @TypeConverter
    fun fromStringToList(string: String): List<Price> = adapter.fromJson(string)!!

}