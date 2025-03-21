package com.example.pokecat.present.main

import android.graphics.Bitmap
import com.example.pokecat.api.ApiService
import com.example.pokecat.api.models.CatResponse
import com.example.pokecat.data.AppDatabase
import com.example.pokecat.data.entities.CatEntity
import com.example.pokecat.present.main.models.Cat
import retrofit2.Response
import javax.inject.Inject

interface MainTask {
    suspend fun fetchCats(): Response<List<CatResponse>>
    suspend fun fetchCatImg(imgId: String): Response<Bitmap>
    suspend fun insertCatList(catListResponse: List<Cat>)
    suspend fun getAllCat(): List<CatEntity>
}

class MainRepository @Inject constructor(private val apiService: ApiService, private val db: AppDatabase) : MainTask {
    override suspend fun fetchCats(): Response<List<CatResponse>> {
        return apiService.fetchCats()
    }

    override suspend fun fetchCatImg(imgId: String): Response<Bitmap> {
        return apiService.fetchCatImg(imgId = imgId)
    }

    override suspend fun insertCatList(catListResponse: List<Cat>) {
        val catEntityList = catListResponse.map { cat ->
            CatEntity(
                weight = cat.weight,
                nameId = cat.id,
                name = cat.name,
                temperament = cat.temperament,
                origin = cat.origin,
                description = cat.description,
                imgName = cat.referenceImageId,
                color = cat.color
            )
        }

        db.catDao().insertCatList(catEntityList)
    }

    override suspend fun getAllCat(): List<CatEntity> {
        return db.catDao().getAllCat()
    }
}