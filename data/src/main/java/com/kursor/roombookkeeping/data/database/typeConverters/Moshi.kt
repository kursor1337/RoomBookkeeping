package com.kursor.roombookkeeping.data.database.typeConverters

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()