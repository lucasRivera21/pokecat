package com.example.pokecat.present.main.models

import android.graphics.Bitmap

data class CatCard(
    val id: Int,
    val name: String,
    val color: String,
    val imgBitmap: Bitmap? = null,
    val isFounded: Boolean = false
)
