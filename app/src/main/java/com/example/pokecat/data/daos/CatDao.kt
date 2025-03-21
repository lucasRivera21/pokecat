package com.example.pokecat.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokecat.data.entities.CatEntity

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatList(catList: List<CatEntity>)

    @Query("SELECT * FROM cat")
    suspend fun getAllCat(): List<CatEntity>
}