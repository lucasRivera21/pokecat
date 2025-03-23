package com.example.pokecat.api

import com.example.pokecat.api.models.CatResponse
import com.example.pokecat.utils.Credentials.Companion.API_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun fetchCats(
        @Header("x-api-key") apiKey: String = API_KEY,
        @Url url: String
    ): Response<List<CatResponse>>

    @GET
    suspend fun fetchCatImg(
        @Header("x-api-key") apiKey: String = API_KEY,
        @Url url: String,
    ): Response<ResponseBody>
}