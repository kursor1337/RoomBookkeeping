package com.kursor.roombookkeeping.data.repositories

import com.kursor.roombookkeeping.data.database.daos.ReceiptDao
import com.kursor.roombookkeeping.data.database.entities.ReceiptEntity
import com.kursor.roombookkeeping.domain.repositories.ReceiptRepository
import com.kursor.roombookkeeping.model.Receipt

class ReceiptRepositoryImpl(
    val receiptDao: ReceiptDao
) : ReceiptRepository {
    override suspend fun get(id: Long): Receipt? = receiptDao.get(id)?.convertToModelEntity()

    override suspend fun getAll(): List<Receipt> = receiptDao.getAll().map { it.convertToModelEntity() }

    override suspend fun save(receipt: Receipt) = receiptDao.insert(receipt.convertToDatabaseEntity())

    override suspend fun edit(receipt: Receipt) = receiptDao.update(receipt.convertToDatabaseEntity())

    override suspend fun delete(receipt: Receipt) = receiptDao.delete(receipt.convertToDatabaseEntity())

    private fun ReceiptEntity.convertToModelEntity(): Receipt = Receipt(
        id = this.id,
        name = this.name,
        dateTime = this.dateTime,
        priceList = this.priceList,
        persons = this.persons
    )

    private fun Receipt.convertToDatabaseEntity(): ReceiptEntity = ReceiptEntity(
        id = this.id,
        name = this.name,
        dateTime = this.dateTime,
        priceList = this.priceList,
        persons = this.persons
    )

}