package com.kursor.roombookkeeping.data.database.typeConverters

import androidx.room.TypeConverter
import com.kursor.roombookkeeping.data.database.entities.PriceEntity
import com.kursor.roombookkeeping.model.Price
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import java.lang.reflect.Type

class PriceListTypeConverter {

    private val priceListType: Type =
        Types.newParameterizedType(List::class.java, PriceEntity::class.java)

    private val adapter: JsonAdapter<List<PriceEntity>> = moshi.adapter(priceListType)

    @TypeConverter
    fun fromPriceToString(priceList: List<PriceEntity>): String = adapter.toJson(priceList)

    @TypeConverter
    fun fromStringToList(string: String): List<PriceEntity> = adapter.fromJson(string)!!

}