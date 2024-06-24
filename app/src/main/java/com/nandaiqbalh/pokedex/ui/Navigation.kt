package com.nandaiqbalh.pokedex.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
	navController: NavHostController = rememberNavController(),
) {

	NavHost(
		navController = navController,
		startDestination = AppScreen.PokemonListScreen.route
	) {

		composable(AppScreen.PokemonListScreen.route) {
			// set view

		}

		composable(AppScreen.PokemonDetailScreen.route + "/{dominantColor}/{pokemonName}",
			arguments = listOf(
				navArgument("dominantColor") {
					type = NavType.IntType
				},

				navArgument("pokemonName") {
					type = NavType.StringType
				}
			)) { entry ->
			val dominantColor = remember {
				val color = entry.arguments?.getInt("dominantColor")
				color?.let { Color(it) } ?: Color.White
			}

			val pokemonName = remember {
				entry.arguments?.getString("pokemonName")
			}

		}
	}

}