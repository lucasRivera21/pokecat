package com.example.pokecat.api

import com.example.pokecat.api.models.CatResponse
import com.example.pokecat.utils.Credentials.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("breeds")
    suspend fun fetchCats(@Header("x-api-key") apiKey: String = API_KEY): Response<List<CatResponse>>
}