package com.kursor.roombookkeeping.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kursor.roombookkeeping.data.database.Database
import com.kursor.roombookkeeping.data.repositories.PersonRepositoryImpl
import com.kursor.roombookkeeping.data.repositories.ReceiptRepositoryImpl
import com.kursor.roombookkeeping.domain.repositories.PersonRepository
import com.kursor.roombookkeeping.domain.repositories.ReceiptRepository
import org.koin.dsl.module

val dataModule = module {

    single {
        Room.databaseBuilder(
            get(),
            Database::class.java,
            "RoomBookkeeper-DB")
            .build()
    }

    single { get<Database>().personDao() }

    single { get<Database>().receiptDao() }

    single<ReceiptRepository> {
        ReceiptRepositoryImpl(receiptDao = get())
    }

    single<PersonRepository> {
        PersonRepositoryImpl(personDao = get())
    }
}