package com.example.pokecat.present.main

import com.example.pokecat.api.ApiHelper
import com.example.pokecat.api.models.CatResponse
import retrofit2.Response

class MainRepository {
    private val apiHelper = ApiHelper()
    
    suspend fun fetchCats(): Response<List<CatResponse>> {
        return apiHelper.apiService.fetchCats()
    }
}