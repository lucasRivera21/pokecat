package com.example.pokecat.navigation

interface Destinations {
    val route: String
}

object MainScreen : Destinations {
    override val route: String
        get() = "MainScreen"
}

object CameraScreen : Destinations {
    override val route: String
        get() = "CameraScreen"
}

object DetailsScreen : Destinations {
    override val route: String
        get() = "DetailsScreen"
}