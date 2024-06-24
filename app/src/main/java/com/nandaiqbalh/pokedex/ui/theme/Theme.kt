package com.nandaiqbalh.pokedex.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.darkColors
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
	primary = Color.Yellow,
	background = Color(0xFF101010),
	onBackground = Color.White,
	surface = Color(0xFF303030),
	onSurface = Color.White
)

private val LightColorPalette = lightColors(
	primary = Color.Blue,
	background = LightBlue,
	onBackground = Color.Black,
	surface = Color.White,
	onSurface = Color.Black
)


@Composable
fun PokedexTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	// Dynamic color is available on Android 12+
	dynamicColor: Boolean = true,
	content: @Composable () -> Unit
) {
	val colors = if (darkTheme) {
		DarkColorPalette
	} else {
		LightColorPalette
	}

	androidx.compose.material.MaterialTheme(
		colors = colors,
		typography = Typography,
		shapes = Shapes,
		content = content
	)
}