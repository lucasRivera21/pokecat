package com.example.pokecat.present.main

import com.example.pokecat.api.ApiService
import com.example.pokecat.api.models.CatResponse
import retrofit2.Response
import javax.inject.Inject

interface MainTask {
    suspend fun fetchCats(): Response<List<CatResponse>>
}

class MainRepository @Inject constructor(private val apiService: ApiService) : MainTask {
    override suspend fun fetchCats(): Response<List<CatResponse>> {
        return apiService.fetchCats()
    }
}