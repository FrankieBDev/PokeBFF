package com.frankie.routes

import com.frankie.models.toPokemonResponse
import com.frankie.services.PokemonService
import com.frankie.services.httpClient
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Configures routing for the Ktor application.
 * This includes handling endpoints and error pages.
 */
fun Application.configureRouting() {
    install(StatusPages) {
        exception<IllegalStateException> { call, cause ->
            call.respondText("App in illegal state as ${cause.message}")
        }
    }
    routing {
        /**
         * GET endpoint to fetch Pokémon data by name.
         */
        get("/pokemon/{name}") {
            val name = call.parameters["name"] ?: return@get call.respondText(
                "Missing name",
                status = HttpStatusCode.BadRequest
            )
            val service = PokemonService(httpClient)

            try {
                val apiResult = service.getPokemon(name)
                val response = apiResult.toPokemonResponse()
                call.respond(response)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound, "Pokemon not found")
            }
        }
    }
}
