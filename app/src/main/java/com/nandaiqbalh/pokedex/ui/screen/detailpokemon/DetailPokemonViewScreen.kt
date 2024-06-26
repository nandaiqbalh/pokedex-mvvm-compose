package com.nandaiqbalh.pokedex.ui.screen.detailpokemon

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.nandaiqbalh.pokedex.R
import com.nandaiqbalh.pokedex.data.remote.response.detail.PokemonDetailResponse
import com.nandaiqbalh.pokedex.data.remote.response.detail.Type
import com.nandaiqbalh.pokedex.util.parseStatToAbbr
import com.nandaiqbalh.pokedex.util.parseStatToColor
import com.nandaiqbalh.pokedex.util.parseTypeToColor
import timber.log.Timber
import java.util.Locale
import kotlin.math.round

@Composable
fun DetailPokemonViewScreen(
	dominantColor: Color,
	pokemonName: String,
	navController: NavController,
	topPadding: Dp = 20.dp,
	pokemonImageSize: Dp = 200.dp,
	viewModel: DetailPokemonViewModel = hiltViewModel(),
) {

	// Call getPokemonDetail only once when the composable is first composed
	LaunchedEffect(key1 = pokemonName) {
		viewModel.getPokemonDetail(pokemonName.lowercase(Locale.getDefault()))
	}

	val pokemonDetail by remember {
		viewModel.pokemonDetail
	}

	Box(
		contentAlignment = Alignment.TopCenter,
		modifier = Modifier.fillMaxSize()
	) {

		PokemonDetailTopSection(
			navController = navController, modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight(0.2f)
				.align(Alignment.TopCenter)
		)

		PokemonDetailStateWrapper(
			modifier = Modifier
				.fillMaxSize()
				.padding(
					top = topPadding + pokemonImageSize / 2f,
					start = 16.dp,
					end = 16.dp,
					bottom = 16.dp
				)
				.shadow(10.dp, RoundedCornerShape(10.dp))
				.clip(RoundedCornerShape(10.dp))
				.background(MaterialTheme.colors.surface)
				.padding(16.dp)
				.align(Alignment.BottomCenter),
			loadingModifier = Modifier
				.size(100.dp)
				.align(Alignment.Center)
				.padding(
					top = topPadding + pokemonImageSize / 2f,
					start = 16.dp,
					end = 16.dp,
					bottom = 16.dp
				)
		)

		if (pokemonDetail != null) {
			pokemonDetail?.sprites?.let {
				SubcomposeAsyncImage(
					model = ImageRequest.Builder(LocalContext.current)
						.data(it.front_default)
						.crossfade(true)
						.build(),
					contentDescription = pokemonDetail?.name,
					loading = {
						CircularProgressIndicator(
							color = MaterialTheme.colors.primary,
							modifier = Modifier.scale(0.5f)
						)

					},
					modifier = Modifier
						.size(pokemonImageSize)
						.offset(y = topPadding),
				)
			}
		}
	}
}

@Composable
fun PokemonDetailTopSection(
	navController: NavController,
	modifier: Modifier = Modifier,
) {

	Box(
		contentAlignment = Alignment.TopStart, modifier = modifier.background(
			Brush.verticalGradient(
				listOf(
					Color.Black,
					Color.Transparent
				)
			)
		)
	) {
		Icon(
			imageVector = Icons.AutoMirrored.Filled.ArrowBack,
			contentDescription = null,
			tint = Color.White,
			modifier = Modifier
				.size(36.dp)
				.offset(16.dp, 16.dp)
				.clickable {
					navController.navigateUp()
				}
		)
	}
}

@Composable
fun PokemonDetailStateWrapper(
	modifier: Modifier = Modifier,
	loadingModifier: Modifier = Modifier,
	viewModel: DetailPokemonViewModel = hiltViewModel(),
) {
	val loadError by remember { viewModel.loadError }
	val isLoading by remember { viewModel.isLoading }

	Timber.tag("LOADINGK").d(isLoading.toString())

	Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		if (isLoading) {
			CircularProgressIndicator(
				color = MaterialTheme.colors.primary,
				modifier = loadingModifier
			)
		} else {
			if (loadError.isNotEmpty()) {
				Text(
					text = loadError,
					color = Color.Red,
					modifier = Modifier.padding(16.dp) // Adjust padding as necessary
				)
			} else {
				viewModel.pokemonDetail.value?.let {
					PokemonDetailSection(
						pokemonInfo = it,
						modifier = Modifier
							.offset(y = (-20).dp)
					)
				}
			}
		}
	}
}

@Composable
fun PokemonDetailSection(
	pokemonInfo: PokemonDetailResponse,
	modifier: Modifier = Modifier
) {
	val scrollState = rememberScrollState()
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = modifier
			.fillMaxSize()
			.offset(y = 100.dp)
			.verticalScroll(scrollState)
	) {
		Text(
			text = "#${pokemonInfo.id} ${pokemonInfo.name.capitalize(Locale.ROOT)}",
			fontWeight = FontWeight.Bold,
			fontSize = 30.sp,
			textAlign = TextAlign.Center,
			color = MaterialTheme.colors.onSurface
		)
		PokemonTypeSection(types = pokemonInfo.types)
		PokemonDetailDataSection(
			pokemonWeight = pokemonInfo.weight,
			pokemonHeight = pokemonInfo.height
		)

		PokemonBaseStats(pokemonInfo = pokemonInfo)

	}
}

@Composable
fun PokemonTypeSection(types: List<Type>) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier
			.padding(16.dp)
	) {
		for(type in types) {
			Box(
				contentAlignment = Alignment.Center,
				modifier = Modifier
					.weight(1f)
					.padding(horizontal = 8.dp)
					.clip(CircleShape)
					.background(parseTypeToColor(type))
					.height(35.dp)
			) {
				Text(
					text = type.type.name.capitalize(Locale.ROOT),
					color = Color.White,
					fontSize = 18.sp
				)
			}
		}
	}
}

@Composable
fun PokemonDetailDataSection(
	pokemonWeight: Int,
	pokemonHeight: Int,
	sectionHeight: Dp = 80.dp
) {
	val pokemonWeightInKg = remember {
		round(pokemonWeight * 100f) / 1000f
	}
	val pokemonHeightInMeters = remember {
		round(pokemonHeight * 100f) / 1000f
	}
	Row(
		modifier = Modifier
			.fillMaxWidth()
	) {
		PokemonDetailDataItem(
			dataValue = pokemonWeightInKg,
			dataUnit = "kg",
			dataIcon = painterResource(id = R.drawable.ic_weight),
			modifier = Modifier.weight(1f)
		)
		Spacer(modifier = Modifier
			.size(1.dp, sectionHeight)
			.background(Color.LightGray))
		PokemonDetailDataItem(
			dataValue = pokemonHeightInMeters,
			dataUnit = "m",
			dataIcon = painterResource(id = R.drawable.ic_height),
			modifier = Modifier.weight(1f)
		)
	}
}

@Composable
fun PokemonDetailDataItem(
	dataValue: Float,
	dataUnit: String,
	dataIcon: Painter,
	modifier: Modifier = Modifier
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
		modifier = modifier
	) {
		Icon(painter = dataIcon, contentDescription = null, tint = MaterialTheme.colors.onSurface)
		Spacer(modifier = Modifier.height(8.dp))
		Text(
			text = "$dataValue$dataUnit",
			color = MaterialTheme.colors.onSurface
		)
	}
}

@Composable
fun PokemonStat(
	statName: String,
	statValue: Int,
	statMaxValue: Float,
	statColor: Color,
	height: Dp = 28.dp,
	animDuration: Int = 1000,
	animDelay: Int = 0
) {
	var animationPlayed by remember {
		mutableStateOf(false)
	}
	val curPercent = animateFloatAsState(
		targetValue = if (animationPlayed) {
			statValue / statMaxValue
		} else 0f,
		animationSpec = tween(
			durationMillis = animDuration,
			delayMillis = animDelay
		)
	)
	LaunchedEffect(key1 = true) {
		animationPlayed = true
	}
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.height(height)
			.clip(CircleShape)
			.background(
				if (isSystemInDarkTheme()) {
					Color(0xFF505050)
				} else {
					Color.LightGray
				}
			)
	) {
		Row(
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.fillMaxHeight()
				.fillMaxWidth(curPercent.value)
				.clip(CircleShape)
				.background(statColor)
				.padding(horizontal = 8.dp)
		) {
			Text(
				text = statName,
				fontWeight = FontWeight.Bold
			)
			Text(
				text = (curPercent.value * statMaxValue).toInt().toString(),
				fontWeight = FontWeight.Bold
			)
		}
	}
}

@Composable
fun PokemonBaseStats(
	pokemonInfo: PokemonDetailResponse,
	animDelayPerItem: Int = 100
) {
	val maxBaseStat = remember {
		pokemonInfo.stats.maxOf { it.base_stat }
	}
	Column(
		modifier = Modifier.fillMaxWidth()
	) {
		Text(
			text = "Base stats:",
			fontSize = 20.sp,
			color = MaterialTheme.colors.onSurface
		)
		Spacer(modifier = Modifier.height(4.dp))

		pokemonInfo.stats.forEachIndexed { index, stat ->
			PokemonStat(
				statName = parseStatToAbbr(stat),
				statValue = stat.base_stat,
				statMaxValue = maxBaseStat.toFloat(),
				statColor = parseStatToColor(stat),
				animDelay = index * animDelayPerItem
			)
			Spacer(modifier = Modifier.height(8.dp))
		}

		// Spacer di bagian paling bawah
		Spacer(modifier = Modifier.height(16.dp))
	}
}
