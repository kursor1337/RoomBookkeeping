package com.kursor.roombookkeeping.data.database

import androidx.room.Database
import com.kursor.roombookkeeping.data.database.daos.PersonDao
import com.kursor.roombookkeeping.data.database.daos.ReceiptDao
import com.kursor.roombookkeeping.data.database.entities.PersonEntity
import com.kursor.roombookkeeping.data.database.entities.ReceiptEntity

@Database(entities = [PersonEntity::class, ReceiptEntity::class], version = 1)
abstract class Database {

    abstract fun personDao(): PersonDao

    abstract fun receiptDao(): ReceiptDao

}