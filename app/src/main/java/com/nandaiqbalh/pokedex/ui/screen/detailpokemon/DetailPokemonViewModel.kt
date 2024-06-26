package com.nandaiqbalh.pokedex.ui.screen.detailpokemon

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.pokedex.data.remote.repository.PokemonRepository
import com.nandaiqbalh.pokedex.data.remote.response.detail.PokemonDetailResponse
import com.nandaiqbalh.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
	private val repository: PokemonRepository,
) : ViewModel() {

	var pokemonDetail = mutableStateOf<PokemonDetailResponse?>(null)
	var loadError = mutableStateOf("")
	var isLoading = mutableStateOf(false)

	fun getPokemonDetail(pokemonName: String) {
		viewModelScope.launch {
			try {
				isLoading.value = true
				val result = repository.getPokemonDetail(pokemonName)

				when (result) {
					is Resource.Success -> {
						pokemonDetail.value = result.data
						isLoading.value = false
					}

					is Resource.Error -> {
						loadError.value = result.message ?: "An error occurred!"
						isLoading.value = false
					}

					is Resource.Empty -> {
						loadError.value = "No data found!"
						isLoading.value = false
					}

					is Resource.Loading -> {
						// Handle loading state if needed
					}
				}
			} catch (e: Exception) {
				Timber.e(e, "Error fetching Pokémon detail")
				loadError.value = "Error fetching Pokémon detail: ${e.message}"
				isLoading.value = false
			}
		}
	}
}
