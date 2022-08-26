package com.kursor.roombookkeeping.data.database.daos

import androidx.room.*
import com.kursor.roombookkeeping.data.database.entities.ReceiptEntity

@Dao
interface ReceiptDao {

    @Query("SELECT * FROM ReceiptEntity")
    suspend fun getAll(): List<ReceiptEntity>

    @Query("SELECT * FROM ReceiptEntity WHERE id LIKE :id")
    suspend fun get(id: Long): ReceiptEntity?

    @Insert
    suspend fun insert(receiptEntity: ReceiptEntity)

    @Delete
    suspend fun delete(receiptEntity: ReceiptEntity)

    @Update
    suspend fun update(receiptEntity: ReceiptEntity)

}