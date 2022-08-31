package com.kursor.roombookkeeping.data.database.typeConverters

import androidx.room.TypeConverter
import com.kursor.roombookkeeping.data.database.entities.PersonEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import java.lang.reflect.Type

class PersonListTypeConverter {


    private val personListType: Type =
        Types.newParameterizedType(List::class.java, PersonEntity::class.java)

    private val adapter: JsonAdapter<List<PersonEntity>> = moshi.adapter(personListType)


    @TypeConverter
    fun fromListToString(personList: List<PersonEntity>): String = adapter.toJson(personList)

    @TypeConverter
    fun fromStringToList(string: String): List<PersonEntity> =
        adapter.fromJson(string) ?: emptyList()

}