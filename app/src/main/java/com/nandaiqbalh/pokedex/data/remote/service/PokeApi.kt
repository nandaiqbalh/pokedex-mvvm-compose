package com.nandaiqbalh.pokedex.data.remote.service

import com.nandaiqbalh.pokedex.data.remote.response.detail.PokemonDetailResponse
import com.nandaiqbalh.pokedex.data.remote.response.list.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

	@GET("pokemon")
	suspend fun getPokemonList(
		@Query("limit") limit: Int,
		@Query("offset") offset: Int,
	): PokemonListResponse

	@GET("pokemon/{name}")
	suspend fun getPokemonDetail(
		@Path("name") name: String,
	): PokemonDetailResponse
}