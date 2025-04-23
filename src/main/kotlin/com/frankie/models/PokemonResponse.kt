package com.frankie.models

import kotlinx.serialization.Serializable

/**
 * A simplified data model returned to the UI.
 * Includes only the essential fields needed by the frontend.
 */

@Serializable
data class PokemonResponse(
    val name: String,
    val imageUrl: String,
    val primaryType: String
)