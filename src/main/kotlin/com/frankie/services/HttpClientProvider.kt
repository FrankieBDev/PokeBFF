package com.frankie.services

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * Provides a preconfigured instance of [HttpClient] to make network requests.
 *
 * Uses the CIO engine and installs content negotiation with JSON serialization.
 *
 * Ignores unknown fields in responses to improve resilience.
 *
 * Enables pretty printing (makes JSON responses easier to read)
 * and leniency (allows things like accepting trailing commas in lists/objects, unquoted keys and some non-standard JSON formats)
 * for easier debugging and parsing.
 */

val httpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        })
    }
}