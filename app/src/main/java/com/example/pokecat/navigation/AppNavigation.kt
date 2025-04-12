package com.example.pokecat.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokecat.present.camera.CameraScreen
import com.example.pokecat.present.details.DetailsScreen
import com.example.pokecat.present.main.MainScreen

@Composable
fun AppNavigation(paddingValues: PaddingValues) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = MainScreen.route) {
        composable(MainScreen.route) { MainScreen(navController = navController) }

        composable(CameraScreen.route) { CameraScreen() }

        composable("${DetailsScreen.route}/{card_id}/{photo_uri}", arguments = listOf(
            navArgument("card_id") {
                type = NavType.IntType
                nullable = false
                defaultValue = -5
            },
            navArgument("photo_uri") {
                type = NavType.StringType
                nullable = true
            }
        )) { entry ->
            val cardId = entry.arguments?.getInt("card_id")
            val photoUri = entry.arguments?.getString("photo_uri")
            DetailsScreen(cardId, photoUri, navController)
        }
    }
}