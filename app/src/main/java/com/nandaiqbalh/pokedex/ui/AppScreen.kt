package com.nandaiqbalh.pokedex.ui

sealed class AppScreen(val name: String, val route: String) {
	object PokemonListScreen : AppScreen(name = "Pokemon List", route = "pokemon_list_screen")
	object PokemonDetailScreen : AppScreen(name = "Pokemon Detail", route = "pokemon_detail_screen")
}