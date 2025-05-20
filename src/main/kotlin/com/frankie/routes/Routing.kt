package com.frankie.routes

import com.frankie.services.PokemonService
import com.frankie.services.httpClient
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Sets up all routing and error handling for the Ktor application.
 * Includes endpoints for basic health check and Pokémon-related data.
 */
fun Application.configureRouting(pokemonService: PokemonService = PokemonService(httpClient)) {
    // Global error handling setup
    install(StatusPages) {
        exception<IllegalStateException> { call, cause ->
            call.respondText("App in illegal state as ${cause.message}")
        }
    }
    routing {
        /**
         * Health check or root endpoint.
         * Confirms the server is running.
         */
        get("/") {
            call.respondText("PokeBFF is running!", ContentType.Text.Plain)
        }

        // DELETE WHEN DONE - COMMENTED AS REMINDER TO SELF
//        /**
//         * Mock endpoint that returns a hardcoded list of basic Pokémon data.
//         * This is for initial testing.
//         */
//        get("/pokemon") {
//            val list = listOf(
//                mapOf("name" to "bulbasaur", "url" to "https://pokeapi.co/api/v2/pokemon/1/"),
//                mapOf("name" to "charmander", "url" to "https://pokeapi.co/api/v2/pokemon/4/"),
//                mapOf("name" to "squirtle", "url" to "https://pokeapi.co/api/v2/pokemon/7/")
//            )
//            call.respond(list)
//        }

        get("/pokemon") {
            val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 20
            val offset = call.request.queryParameters["offset"]?.toIntOrNull() ?: 0

            try {
                val response = pokemonService.getPokemonList(limit = limit, offset = offset)
                call.respond(HttpStatusCode.OK, response)
            } catch (exception: Exception) {
                call.respond(HttpStatusCode.BadRequest, exception.message ?: "Failed to fetch Pokemon List")
            }
        }

        /**
         * Returns detailed information for a specific Pokémon based on its name.
         * Responds with 400 if name is missing, or 404 if Pokémon is not found.
         */
        get("/pokemon/{name}") {
            val name = call.parameters["name"] ?: return@get call.respondText(
                "Missing name",
                status = HttpStatusCode.BadRequest
            )

            try {
                val apiResult = pokemonService.getPokemon(name)
                call.respond(HttpStatusCode.OK, apiResult)
            } catch (_: Exception) {
                call.respond(HttpStatusCode.NotFound, "Pokemon not found")
            }
        }
    }
}
