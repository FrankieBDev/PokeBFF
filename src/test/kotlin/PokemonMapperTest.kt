package com.frankie

import com.frankie.models.*
import kotlin.test.Test
import kotlin.test.assertEquals

class PokemonMapperTest {

    @Test
    fun `test mapToPokemonResponse correctly transforms PokemonApiResponse`() {
        val apiResponse = PokemonApiResponse(
            id = 6,
            name = "charizard",
            types = listOf(
                TypeSlot(slot = 1, type = Type("fire")),
                TypeSlot(slot = 2, type = Type("flying"))
            ),
            sprites = Image("https://img.pokemondb.net/sprites/charizard.png")
        )

        val expected = PokemonResponse(
            name = "Charizard",
            primaryType = "Fire",
            imageUrl = "https://img.pokemondb.net/sprites/charizard.png"
        )
        val result = apiResponse.toPokemonResponse()

        assertEquals(expected, result)
    }
}