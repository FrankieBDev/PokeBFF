package com.frankie.models

import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    val results: List<PokemonResult>
)

@Serializable
data class PokemonResult(
    val name: String,
    val url: String
)

@Serializable
data class PokemonResponse(
    val name: String,
    val imageUrl: String,
    val primaryType: String
)