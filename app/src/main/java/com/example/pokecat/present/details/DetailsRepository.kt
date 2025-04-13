package com.example.pokecat.present.details

import com.example.pokecat.data.AppDatabase
import com.example.pokecat.data.entities.CatEntity
import javax.inject.Inject

class DetailsRepository @Inject constructor(private val db: AppDatabase) {
    suspend fun getCard(cardId: Int): CatEntity {
        return db.catDao().getCat(cardId)
    }

}