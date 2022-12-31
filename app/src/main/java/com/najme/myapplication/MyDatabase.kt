package com.najme.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.najme.duniFood.Food

@Database(entities = [Food::class], version = 1, exportSchema = false)
abstract class MyDatabase:RoomDatabase() {
    abstract val foodDao:foodDao
    companion object{
        private var database:MyDatabase? = null
        fun getDatabase(context: Context):MyDatabase{
            if(database==null){
                database= Room.databaseBuilder(context.applicationContext,MyDatabase::class.java,"myDatabase.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return database!!
        }
    }

}