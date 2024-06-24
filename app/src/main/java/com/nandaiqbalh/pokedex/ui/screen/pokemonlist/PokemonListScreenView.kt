package com.nandaiqbalh.pokedex.ui.screen.pokemonlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PokemonListScreenView(
	navController: NavController,
) {

	Surface(
		color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()
	) {

		Column {
			Spacer(modifier = Modifier.height(20.dp))

			SearchBar(hint = "Search...", modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp)) {

			}

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