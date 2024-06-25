package com.nandaiqbalh.pokedex.ui.screen.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.nandaiqbalh.pokedex.data.model.PokedexListEntry
import com.nandaiqbalh.pokedex.data.remote.repository.PokemonRepository
import com.nandaiqbalh.pokedex.util.Constant.PAGE_SIZE
import com.nandaiqbalh.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PokemonListScreenViewModel @Inject constructor(
	private val repository: PokemonRepository,
) :
	ViewModel() {

	private var curPage = 0

	var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
	var loadError = mutableStateOf("")
	var isLoading = mutableStateOf(false)
	var endReached = mutableStateOf(false)

	fun loadPokemonPaginated() {
		viewModelScope.launch {
			isLoading.value = true
			val result = repository.getPokemonList(PAGE_SIZE, curPage * PAGE_SIZE)

			when (result) {
				is Resource.Success -> {

					// set end reach value
					endReached.value = curPage * PAGE_SIZE >= result.data.count

					val pokedexEntries = result.data.results.mapIndexed { index, result ->

						val number = if (result.url.endsWith("/")) {
							result.url.dropLast(1).takeLastWhile { it.isDigit() }
						} else {
							result.url.takeLastWhile { it.isDigit() }
						}

						var url =
							"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"

						// construct pokedex entry
						PokedexListEntry(
							pokemonName = result.name.capitalize(Locale.ROOT),
							imageUrl = url,
							number = number.toInt()
						)
					}

					curPage++

					loadError.value = ""
					isLoading.value = false

					pokemonList.value += pokedexEntries

				}

				is Resource.Error -> {
					loadError.value = result.message ?: "Error occured!"
					isLoading.value = false
				}

				is Resource.Empty -> TODO()
				is Resource.Loading -> TODO()
			}
		}
	}

	fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
		val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

		Palette.from(bmp).generate { pallete ->
			pallete?.dominantSwatch?.rgb?.let { colorValue ->
				onFinish(Color(colorValue))
			}
		}
	}
}