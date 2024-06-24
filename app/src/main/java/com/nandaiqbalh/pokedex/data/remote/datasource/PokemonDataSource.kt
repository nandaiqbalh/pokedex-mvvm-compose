package com.nandaiqbalh.pokedex.data.remote.datasource

import com.nandaiqbalh.pokedex.data.remote.response.detail.PokemonDetailResponse
import com.nandaiqbalh.pokedex.data.remote.response.list.PokemonListResponse
import com.nandaiqbalh.pokedex.data.remote.service.PokeApi
import javax.inject.Inject

interface PokemonDataSource {

	suspend fun getPokemonList(limit: Int, offset: Int): PokemonListResponse

	suspend fun getPokemonDetail(pokemonName: String): PokemonDetailResponse
}

class PokemonDataSourceImpl @Inject constructor(private val pokeApi: PokeApi) : PokemonDataSource {
	override suspend fun getPokemonList(limit: Int, offset: Int): PokemonListResponse {
		return pokeApi.getPokemonList(limit, offset)
	}

	override suspend fun getPokemonDetail(pokemonName: String): PokemonDetailResponse {
		return pokeApi.getPokemonDetail(pokemonName)
	}
}