package com.frankie.models

fun PokemonApiResponse.toPokemonResponse(): PokemonResponse {
    return PokemonResponse(
        name = this.name.replaceFirstChar { it.uppercase() },
        imageUrl = this.sprites.imageUrl,
        primaryType = this.types.firstOrNull()?.type?.name?.replaceFirstChar { it.uppercaseChar() } ?: "Unknown"
    )
}