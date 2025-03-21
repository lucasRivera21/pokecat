package com.example.pokecat.present.main

import android.graphics.Bitmap
import com.example.pokecat.api.ApiService
import com.example.pokecat.api.models.CatResponse
import retrofit2.Response
import javax.inject.Inject

interface MainTask {
    suspend fun fetchCats(): Response<List<CatResponse>>
    suspend fun fetchCatImg(imgId: String): Response<Bitmap>
}

class MainRepository @Inject constructor(private val apiService: ApiService) : MainTask {
    override suspend fun fetchCats(): Response<List<CatResponse>> {
        return apiService.fetchCats()
    }

    override suspend fun fetchCatImg(imgId: String): Response<Bitmap> {
        return apiService.fetchCatImg(imgId = imgId)
    }

}