package com.nandaiqbalh.pokedex.data.remote.response.list

data class PokemonListResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)