package com.example.pokecat.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokecat.data.daos.CatDao
import com.example.pokecat.data.entities.CatEntity

@Database(entities = [CatEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
}