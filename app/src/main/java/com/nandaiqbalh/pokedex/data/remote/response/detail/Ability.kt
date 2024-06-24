package com.nandaiqbalh.pokedex.data.remote.response.detail

data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)