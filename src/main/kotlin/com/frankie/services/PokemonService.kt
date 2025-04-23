package com.frankie.services

import com.frankie.models.PokemonApiResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

/**
 * Service class to fetch Pokémon data from the PokeAPI.
 *
 * @param client The configured HttpClient to perform API calls.
 */

class PokemonService(private val client: HttpClient) {
    /**
     * Retrieves a single Pokémon by name from the PokeAPI.
     *
     * @param name The name of the Pokémon to fetch.
     * @return Parsed [PokemonApiResponse] object.
     */
    suspend fun getPokemon(name: String): PokemonApiResponse {
        return client.get("https://pokeapi.co/api/v2/pokemon/$name") {
            accept(ContentType.Application.Json)
        }.body()
    }
}
