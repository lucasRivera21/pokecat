package com.example.pokecat.present.main.models

import android.graphics.Bitmap

data class Cat(
    val weight: String,
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    val referenceImageId: String? = null,
    val color: String,
    val imgBitmap: Bitmap? = null,
)