package com.example.pokecat.utils

import com.example.pokecat.BuildConfig

class Credentials {
    companion object {
        const val BASE_URL = "https://api.thecatapi.com/v1/"
        const val API_KEY = BuildConfig.API_KEY
    }
}