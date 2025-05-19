package com.frankie.services

import com.frankie.models.PokemonApiResponse
import com.frankie.models.PokemonListResponse
import com.frankie.models.PokemonResponse
import com.frankie.models.toPokemonResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*


class PokemonService(private val client: HttpClient) {
    suspend fun getPokemon(name: String): PokemonApiResponse {
        val response = client.get("https://pokeapi.co/api/v2/pokemon/$name")

        if (!response.status.isSuccess()) {
            val errorBody = response.bodyAsText()
            throw ClientRequestException(response, errorBody)
        }

        return response.body()
    }

    suspend fun getPokemonList(offset: Int = 0, limit: Int = 20): List<PokemonResponse> {
        val listResponse: PokemonListResponse = client.get("https://pokeapi.co/api/v2/pokemon") {
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()

        return listResponse.results.map { pokemonResult ->
            val detail = getPokemon(pokemonResult.name)
            detail.toPokemonResponse()
        }
    }


}

