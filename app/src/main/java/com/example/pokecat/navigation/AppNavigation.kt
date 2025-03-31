package com.example.pokecat.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokecat.present.camera.CameraScreen
import com.example.pokecat.present.details.DetailsScreen
import com.example.pokecat.present.main.MainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = MainScreen.route) {
        composable(MainScreen.route) { MainScreen(navController = navController) }

        composable(CameraScreen.route) { CameraScreen() }

        composable("${DetailsScreen.route}/{photo_uri}") {
            val photoUri = it.arguments?.getString("photo_uri")
            DetailsScreen(photoUri)
        }
    }
}