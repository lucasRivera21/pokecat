package com.example.pokecat.utils

import com.example.pokecat.BuildConfig

class Credentials {
    companion object {
        const val BASE_URL = "https://api.thecatapi.com/v1/"
        const val BASE_URL_IMG = "https://cdn2.thecatapi.com/"
        const val API_KEY = BuildConfig.API_KEY

        val BG_COLOR_LIST = listOf(
            "#43AA8B",
            "#F94144",
            "#2D9CDB",
            "#F8961E",
            "#F765A3",
            "#7987FF",
            "#90BE6D",
            "#F3722C",
            "#A155B9"
        )
        const val BG_DONT_FOUND_CAT = "#577590"

        const val MAX_IMG_COMMUNITY = 10

        const val MAX_RECOGNITION_CAT_RESULTS = 5

        const val NAME_CACHE_FILE = "cache_photo.jpg"

        val NAME_LIST_IN_MODEL = listOf(
            "Abyssinian",
            "Bengal",
            "Birman",
            "Bombay",
            "British Shorthair",
            "Egyptian Mau",
            "Maine Coon",
            "Persian",
            "Ragdoll",
            "Russian Blue",
            "Siamese",
            "Sphynx"
        )
    }
}