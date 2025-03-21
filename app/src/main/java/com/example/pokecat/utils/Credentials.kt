package com.example.pokecat.utils

import com.example.pokecat.BuildConfig

class Credentials {
    companion object {
        const val BASE_URL = "https://api.thecatapi.com/v1/"
        const val API_KEY = BuildConfig.API_KEY

        val BG_COLOR_LIST = listOf(
            0xFF43AA8B,
            0xFFF94144,
            0xFF2D9CDB,
            0xFFF8961E,
            0xFFF765A3,
            0xFF7987FF,
            0XFF90BE6D,
            0XFFF3722C,
            0XFFA155B9
        )

        const val MAX_IMG_COMMUNITY = 10
    }
}