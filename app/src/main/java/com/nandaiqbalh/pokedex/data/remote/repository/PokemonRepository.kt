package com.nandaiqbalh.pokedex.data.remote.repository

import com.nandaiqbalh.pokedex.data.remote.datasource.PokemonDataSource
import com.nandaiqbalh.pokedex.data.remote.response.detail.PokemonDetailResponse
import com.nandaiqbalh.pokedex.data.remote.response.list.PokemonListResponse
import com.nandaiqbalh.pokedex.util.Resource
import javax.inject.Inject

interface PokemonRepository {
	suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonListResponse>

	suspend fun getPokemonDetail(pokemonName: String): Resource<PokemonDetailResponse>
}

class PokemonRepositoryImpl @Inject constructor(private val pokemonDataSource: PokemonDataSource) :
	PokemonRepository {
	override suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonListResponse> {
		return proceed {
			pokemonDataSource.getPokemonList(limit, offset)
		}
	}

	override suspend fun getPokemonDetail(pokemonName: String): Resource<PokemonDetailResponse> {
		return proceed {
			pokemonDataSource.getPokemonDetail(pokemonName)
		}
	}

	private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
		return try {
			Resource.Success(coroutines.invoke())
		} catch (e: Exception) {
			Resource.Error(e)
		}
	}
}