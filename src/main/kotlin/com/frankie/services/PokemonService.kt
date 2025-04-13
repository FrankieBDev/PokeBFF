package com.frankie.services

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(val name: String)

@Serializable
data class PokemonApiResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("types") val types: List<TypeSlot>,
    @SerialName("sprites") val sprites: Image,
)

@Serializable
data class TypeSlot(
    val slot: Int,
    val type: Type
)

@Serializable
data class Type(
    val name: String
)

@Serializable
data class Image(
    @SerialName("front_default") val imageUrl: String,
)