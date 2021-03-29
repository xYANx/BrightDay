package com.navoichykyan.brightday.data.db

import androidx.room.*

@Dao
interface CityDao{
    @Query("SELECT * FROM cityTable")
    fun getAll(): List<CityEntity>

    @Query("SELECT * FROM cityTable WHERE id LIKE :id")
    fun getById(id: Int): CityEntity

    @Insert
    fun insert(cityEntity: CityEntity)

    @Delete
    fun delete(cityEntity: CityEntity)

    @Update
    fun update(cityEntity: CityEntity)
}