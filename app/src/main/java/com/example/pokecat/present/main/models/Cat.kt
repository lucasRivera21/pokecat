package com.example.pokecat.present.main.models

data class Cat(
    val weight: Weight,
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val countryCodes: String,
    val description: String,
    val referenceImageId: String? = null,
    val color: Long
)

data class Weight(
    val imperial: String,
    val metric: String
)