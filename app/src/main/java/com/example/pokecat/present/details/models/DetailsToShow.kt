package com.example.pokecat.present.details.models

import android.graphics.Bitmap

data class DetailsToShow(
    val weight: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    val img: Bitmap?,
    val color: String,
)
