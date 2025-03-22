package com.example.pokecat.utils

import android.graphics.Color


class Utilities {
    companion object {
        fun hexToColor(hex: String): Int {
            return Color.parseColor(hex)
        }
    }
}