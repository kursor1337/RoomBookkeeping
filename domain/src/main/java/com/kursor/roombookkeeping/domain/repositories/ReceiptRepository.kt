package com.kursor.roombookkeeping.domain.repositories

import com.kursor.roombookkeeping.model.Receipt

interface ReceiptRepository {

    fun get(id: Long): Receipt?

    fun getAll(): List<Receipt>

    fun save(receipt: Receipt)

    fun edit(receipt: Receipt)

    fun delete(receipt: Receipt)
}