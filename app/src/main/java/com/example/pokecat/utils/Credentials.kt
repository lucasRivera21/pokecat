package com.example.pokecat.utils

import com.example.pokecat.BuildConfig

class Credentials {
    companion object {
        const val BASE_URL = "https://api.thecatapi.com/v1/"
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

        const val MAX_IMG_COMMUNITY = 10
    }
}