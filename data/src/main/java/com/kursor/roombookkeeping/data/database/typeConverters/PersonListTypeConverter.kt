package com.kursor.roombookkeeping.data.database.typeConverters

import android.app.Person
import androidx.room.Entity
import androidx.room.TypeConverter
import com.kursor.roombookkeeping.data.database.entities.PersonEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class PersonListTypeConverter {


    val personListType = Types.newParameterizedType(List::class.java, PersonEntity::class.java)

    val adapter: JsonAdapter<List<PersonEntity>> = moshi.adapter(personListType)


    @TypeConverter
    fun fromListToString(personList: List<PersonEntity>): String = adapter.toJson(personList)

    @TypeConverter
    fun fromStringToList(string: String): List<PersonEntity> =
        adapter.fromJson(string) ?: emptyList()

}