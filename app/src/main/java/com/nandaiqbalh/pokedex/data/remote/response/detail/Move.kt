package com.nandaiqbalh.pokedex.data.remote.response.detail

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)