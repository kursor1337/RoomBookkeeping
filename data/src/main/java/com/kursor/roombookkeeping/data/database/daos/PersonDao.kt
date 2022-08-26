package com.kursor.roombookkeeping.data.database.daos

import androidx.room.*
import com.kursor.roombookkeeping.data.database.entities.PersonEntity

@Dao
interface PersonDao {

    @Query("SELECT * FROM person")
    suspend fun getAll(): List<PersonEntity>

    @Query("SELECT * FROM person WHERE id LIKE :id LIMIT 1")
    suspend fun get(id: Long): PersonEntity?

    @Insert
    suspend fun insert(personEntity: PersonEntity)

    @Delete
    suspend fun delete(personEntity: PersonEntity)

    @Update
    suspend fun update(personEntity: PersonEntity)
}