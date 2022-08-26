package com.kursor.roombookkeeping.domain.repositories

import com.kursor.roombookkeeping.model.Receipt

interface ReceiptRepository {

    suspend fun get(id: Long): Receipt?

    suspend fun getAll(): List<Receipt>

    suspend fun save(receipt: Receipt)

    suspend fun edit(receipt: Receipt)

    suspend fun delete(receipt: Receipt)
}