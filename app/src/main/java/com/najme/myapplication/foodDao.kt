package com.najme.myapplication

import androidx.room.*
import com.najme.duniFood.Food

@Dao
interface foodDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate (food: Food)
    @Insert
    fun insertAllFoods(data :List<Food>)

    @Delete
    fun deleteFood(food:Food)

    @Query("SELECT * FROM table_food")
    fun getAllFoods():List<Food>
    @Query("SELECT * FROM table_food WHERE txtSubject LIKE '%'  ||:searching||'%'")
    fun searchFoods(searching:String):List<Food>
}