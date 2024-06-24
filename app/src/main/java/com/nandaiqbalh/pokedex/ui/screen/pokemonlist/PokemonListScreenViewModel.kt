package com.nandaiqbalh.pokedex.ui.screen.pokemonlist

import androidx.lifecycle.ViewModel
import com.nandaiqbalh.pokedex.data.remote.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListScreenViewModel @Inject constructor(
	private val repository: PokemonRepository,
) :
	ViewModel() {
}