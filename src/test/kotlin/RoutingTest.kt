package com.frankie

import com.frankie.models.Image
import com.frankie.models.PokemonApiResponse
import com.frankie.models.Type
import com.frankie.models.TypeSlot
import com.frankie.routes.configureRouting
import com.frankie.services.PokemonService
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*


class RoutingTest {

    @Test
    fun `returns OK and Pokemon data when valid name is provided`() = testApplication {
        val mockService = mockk<PokemonService>()
        val fakeApiResponse = PokemonApiResponse(
            id = 25,
            name = "Pikachu",
            types = listOf(TypeSlot(1, Type("electric"))),
            sprites = Image("https://img.pokemondb.net/sprites/pikachu.png")
        )

        coEvery { mockService.getPokemon("pikachu") } returns fakeApiResponse

        application {
            this.install(ContentNegotiation) {
                json()
            }
            configureRouting(mockService)
        }

        // Act
        val response = client.get("/pokemon/pikachu") {
            accept(ContentType.Application.Json)
        }

        // Assert
        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.bodyAsText()
        assertTrue(body.contains("Pikachu"))
    }

}

