package com.navoichykyan.brightday.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cityTable")
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val city: String)