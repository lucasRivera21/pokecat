package com.example.pokecat.present.main

import com.example.pokecat.api.ApiService
import com.example.pokecat.api.models.CatResponse
import com.example.pokecat.data.AppDatabase
import com.example.pokecat.data.entities.CatEntity
import com.example.pokecat.present.main.models.Cat
import com.example.pokecat.utils.Credentials.Companion.BASE_URL
import com.example.pokecat.utils.Credentials.Companion.BASE_URL_IMG
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

interface MainTask {
    suspend fun fetchCats(): Response<List<CatResponse>>
    suspend fun fetchCatImg(imgId: String): Response<ResponseBody>
    suspend fun insertCatList(catListResponse: List<Cat>)
    suspend fun getAllCat(): List<CatEntity>
    suspend fun updateCatImg(catImgId: String, imgName: String)
}

class MainRepository @Inject constructor(
    private val apiService: ApiService,
    private val db: AppDatabase
) : MainTask {
    override suspend fun fetchCats(): Response<List<CatResponse>> {
        return apiService.fetchCats(url = "${BASE_URL}breeds")
    }

    override suspend fun fetchCatImg(imgId: String): Response<ResponseBody> {
        return apiService.fetchCatImg(url = "${BASE_URL_IMG}images/${imgId}.jpg")
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

    override suspend fun updateCatImg(catImgId: String, imgName: String) {
        db.catDao().updateCatImg(catImgId, imgName)
    }
}