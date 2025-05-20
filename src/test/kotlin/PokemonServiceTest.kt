package com.frankie

import com.frankie.services.PokemonService
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.coroutines.test.runTest


class PokemonServiceTest {

    @Test
    fun `testGetPokemon returns a valid response`() = runBlocking {
        val mockEngine = MockEngine { request ->
            respond(
                content = """
                    {
                        "id": 25,
                        "name": "pikachu",
                        "types": [
                            { "slot": 1, "type": { "name": "electric" } }
                        ],
                        "sprites": { "front_default": "https://img.pokemondb.net/sprites/pikachu.png" }
                    }
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val service = PokemonService(client)

        // Act
        val result = service.getPokemon("pikachu")

        // Assert
        assertEquals(25, result.id)
        assertEquals("pikachu", result.name)
        assertEquals("electric", result.types[0].type.name)
        assertEquals("https://img.pokemondb.net/sprites/pikachu.png", result.sprites.imageUrl)
    }

    @Test
    fun `testGetPokemon returns an error for invalid name`() = runTest {
        val mockEngine = MockEngine { _ ->
            respond(
                content = "Pokemon not found",
                status = HttpStatusCode.NotFound,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val service = PokemonService(client)

        val exception = assertFailsWith<ClientRequestException> {
            service.getPokemon("notavalidname")
        }

        assertEquals(HttpStatusCode.NotFound, exception.response.status)
    }


}