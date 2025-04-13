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

    @Query("UPDATE cat SET imgName = :imgName WHERE imgName = :catImgId")
    suspend fun updateCatImg(catImgId: String, imgName: String)

    @Query("SELECT * FROM cat WHERE id = :cardId")
    suspend fun getCat(cardId: Int): CatEntity
}