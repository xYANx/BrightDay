package com.navoichykyan.brightday.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CityEntity::class], version = 1, exportSchema = false)
abstract class CityDatabase : RoomDatabase(){
    abstract fun cityDao(): CityDao
    companion object{
        @Volatile
        var instance: CityDatabase? = null

        fun getDatabase(context: Context): CityDatabase? {
            if(instance == null){
                synchronized(CityDatabase::class){
                    instance = Room.databaseBuilder(context,CityDatabase::class.java,"myDB").allowMainThreadQueries().build()
                }
            }
            return instance
        }
        fun destroyDataBase(){
            instance = null
        }
    }
}