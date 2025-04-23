package com.frankie.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data model that maps the structure of the PokéAPI response
 * for a single Pokémon, including its ID, name, types, and image.
 */
@Serializable
data class PokemonApiResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("types") val types: List<TypeSlot>,
    @SerialName("sprites") val sprites: Image,
)

/**
 * Data model representing an entry in the list of types for a Pokémon.
 * Each entry contains the type details and its position (slot).
 */
@Serializable
data class TypeSlot(
    val slot: Int,
    val type: Type
)

/**
 * Defines a Pokémon type, such as "fire", "grass", or "water".
 */
@Serializable
data class Type(
    val name: String
)

/**
 * Holds the main image (sprite) URL for the Pokémon.
 * Currently only includes the default front-facing sprite.
 */
@Serializable
data class Image(
    @SerialName("front_default") val imageUrl: String,
)