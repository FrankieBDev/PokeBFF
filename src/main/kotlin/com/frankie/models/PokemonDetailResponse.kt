package com.frankie.models

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val types: List<PokemonType>,
    val sprites: Sprites
)

@Serializable
data class PokemonType(
    val slot: Int,
    val type: TypeDetails
)

@Serializable
data class TypeDetails(
    val name: String
)

@Serializable
data class Sprites(
    val imageUrl: String
)
