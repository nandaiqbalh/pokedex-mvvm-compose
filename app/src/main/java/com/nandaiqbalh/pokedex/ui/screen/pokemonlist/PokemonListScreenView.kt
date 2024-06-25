package com.nandaiqbalh.pokedex.ui.screen.pokemonlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.nandaiqbalh.pokedex.data.model.PokedexListEntry
import com.nandaiqbalh.pokedex.ui.theme.RobotoCondensed

@Composable
fun PokemonListScreenView(
	navController: NavController,
) {
	val viewModel: PokemonListScreenViewModel = hiltViewModel()
	Surface(
		color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()
	) {

		Column {
			Spacer(modifier = Modifier.height(20.dp))

			SearchBar(
				hint = "Search...", modifier = Modifier
					.fillMaxWidth()
					.padding(16.dp)
			) {

			}

			PokedexEntries(navController = navController, viewModel = viewModel)

		}
	}

}

@Composable
fun SearchBar(
	modifier: Modifier = Modifier,
	hint: String = "",
	onSearch: (String) -> Unit = {},
) {

	var text by remember {
		mutableStateOf("")
	}

	var isHintDisplayed by remember {
		mutableStateOf(hint != "")
	}

	Box(modifier = modifier) {
		BasicTextField(
			value = text,
			onValueChange = {
				text = it
				onSearch(it)
			},
			maxLines = 1,
			singleLine = true,
			textStyle = TextStyle(color = Color.Black),
			modifier = Modifier
				.fillMaxWidth()
				.shadow(5.dp, CircleShape)
				.background(Color.White, CircleShape)
				.padding(horizontal = 20.dp, vertical = 12.dp)
				.onFocusChanged { focusState ->
					isHintDisplayed = !focusState.isFocused
				}
		)

		if (isHintDisplayed) {
			Text(
				text = hint,
				color = Color.LightGray,
				modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
			)
		}
	}

}

@Composable
fun PokedexEntries(
	navController: NavController,
	viewModel: PokemonListScreenViewModel,
) {
	val pokemonList by remember { viewModel.pokemonList }
	val endReached by remember { viewModel.endReached }
	val isLoading by remember { viewModel.isLoading }
	val loadError by remember { viewModel.loadError }

	LazyVerticalGrid(
		GridCells.Fixed(2),
		modifier = Modifier
	) {
		items(pokemonList) { entry ->
			PokedexEntry(
				entry = entry,
				navController = navController,
				modifier = Modifier.padding(8.dp)
			)
		}
	}

	// Load more data if not reached the end
	if (!endReached && !isLoading) {
		viewModel.loadPokemonPaginated()
	}

	Box(
		contentAlignment = Center,
		modifier = Modifier.fillMaxSize()
	) {

		if (loadError.isNotEmpty()) {
			RetrySection(error = loadError) {
				viewModel.loadPokemonPaginated()
			}
		}
	}

}

@Composable
fun PokedexEntry(
	entry: PokedexListEntry,
	navController: NavController,
	modifier: Modifier = Modifier,
	viewModel: PokemonListScreenViewModel = hiltViewModel(),
) {
	val defaultDominantColor = MaterialTheme.colors.surface

	var dominantColor by remember {
		mutableStateOf(defaultDominantColor)
	}

	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier
			.shadow(5.dp, RoundedCornerShape(10.dp))
			.clip(RoundedCornerShape(10.dp))
			.aspectRatio(1f)
			.background(
				Brush.verticalGradient(
					listOf(
						dominantColor,
						defaultDominantColor
					)
				)
			)
			.clickable {
				navController.navigate(
					"pokemon_detail_screen/${dominantColor.toArgb()}/${entry.pokemonName}"
				)
			}
	) {
		Column {

			SubcomposeAsyncImage(
				model = ImageRequest.Builder(LocalContext.current)
					.data(entry.imageUrl)
					.crossfade(true)
					.build(),
				contentDescription = entry.pokemonName,
				loading = {

					if (viewModel.isLoading.value) {
						CircularProgressIndicator(
							color = MaterialTheme.colors.primary,
							modifier = Modifier.scale(0.5f)
						)
					}

				},
				onSuccess = {
					viewModel.calcDominantColor(it.result.drawable) { color ->
						dominantColor = color
					}
				},
				modifier = Modifier
					.size(120.dp)
					.align(CenterHorizontally),
			)

			Text(
				text = entry.pokemonName,
				fontFamily = RobotoCondensed,
				fontSize = 20.sp,
				textAlign = TextAlign.Center,
				modifier = Modifier.fillMaxWidth()
			)
		}

	}
}

@Composable
fun RetrySection(
	error: String,
	onRetry: () -> Unit,
) {
	Column {
		Text(error, color = Color.Red, fontSize = 18.sp)
		Spacer(modifier = Modifier.height(8.dp))
		Button(
			onClick = { onRetry() },
			modifier = Modifier.align(CenterHorizontally)
		) {
			Text(text = "Retry")
		}
	}
}




