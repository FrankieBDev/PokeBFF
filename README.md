# PokeBFF


### TL:DR;

A simple Kotlin-based API built with Ktor. This backend is a BFF (Backend-for-Frontend) layer for my [Pokedex Android App](https://github.com/FrankieBDev/PokedexApp). It fetches data from a public API, [PokéAPI](https://pokeapi.co/), simplifies it, and returns clean, frontend-friendly JSON to power the app.

Created as a learning exercise to better understand the relationship between the BFF and Android App at the company I work for.  
  
---
### Tech Stack

- Kotlin (JVM)
- Ktor (server + client)
- CIO Engine (for async HTTP requests)
- Kotlinx Serialization (for JSON)
- Gradle
---
### How to Run It Locally

#### Prerequisites
- JDK 17+
- Gradle
    
#### Run the Server

git clone https://github.com/FrankieBDev/PokeBFF
cd PokeBFF
gradle run
Server runs on port 8080 by default.

Tip: Use http://localhost:8080 or http://10.0.2.2:8080 when calling from Android emulator

---

### What It Does

This backend is a simple Kotlin-based API built with Ktor. 

It acts as a BFF (Backend for Frontend), a middle layer between the public [PokeAPI](https://pokeapi.co/) and the Android frontend. 

#### The purpose of a BFF:

- Acts as a tailored middle layer between the frontend (Android app in this case) and external APIs (i.e. [PokéAPI](https://pokeapi.co/)).
    
- Shapes and simplifies raw API data into exactly what the frontend needs.
    
- Reduces over-fetching (retrieving more data than needed) and under-fetching(not getting enough data and having to make multiple requests).
    
- Sends only essential data to save memory and mobile data usage.
    
- Keeps API keys and sensitive logic secure in the backend.
    
- Handles errors and formats responses consistently.
    
- Makes testing easier by allowing controlled, mockable responses.
    
- Enforces separation of concerns (i.e. app focuses on display, BFF handles data).
    
- Decouples frontend from third-party API changes.
    
- Improves performance, flexibility, and long-term maintainability.

The goal is to keep things clean, lightweight, and frontend friendly by shaping the data exactly how the app needs it. 

#### Endpoints:

It exposes three endpoints:

#### GET /

A basic health check endpoint to confirm the BFF is up and running. Just returns a simple text string.

![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXdVcDOTnf0BFYE7rd8WaxjLE2smi8KTgmHj8K4Y5m4GoodQ9JCHfJB4P-eiN8RUEBwj87VF42NhQGgi4wBU88eCxM97uCEjhmPtN2z_mqsAVG5jFuwUfy5pqchKh5l1m8RgcbP8?key=bHq1Y2znP8A7TbF-XHoINA)

#### GET /pokemon

Returns a paginated list of simplified Pokémon (limited to 20), each with:
- name
- imageUrl (sprite)
- primaryType
    
![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXcxzg_VS99-_RgD-bem_dhlNltaosOl-sIstwJV9teBIEPrWhft-PttU_U0aJErUH6N8td3e3Zmsfvyqewt46nPke03CzSCiWo4u9hmPwF0DIS_ei1D9IEhstUMAO86ZNcCdLN4Tg?key=bHq1Y2znP8A7TbF-XHoINA)

The idea here is to quickly fetch just what the app needs to display a scrollable list with the main details of each Pokemon. 

#### GET /pokemon/{name}

Returns detailed data for a single Pokémon, including:
- id
- name
- types (as a list)
- sprite (front-facing image)

![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXfjHsYeX0Rh1Wfh_ilYud_6r7ZJ6yOZM0RM1KusCUbeUQQvEqYN3dym-RBsHzfL3rn-51RhgwqgatvMAvnf5kITh5KHAIY-fSjXglGZhkaurpADq1c9DHReIb84xMnTnqbivywA6Q?key=bHq1Y2znP8A7TbF-XHoINA)

This is used for the ‘detail’ screen where an individual Pokemon is displayed with further information.  For the purpose of this project I kept it quite simple, but the idea would be to add a more comprehensive chunk of information here.

---

### Project Structure Overview

#### com.frankie.services

- PokemonService.kt  
    Handles API calls to [the](https://pokeapi.co) PokeAPI, including list and detailed endpoints.  
    Maps raw API responses into simplified internal models for the frontend.    
    
- httpClient.kt  
    Sets up a Ktor HttpClient with JSON support.  
    Uses lenient and resilient parsing to handle flexible or incomplete API responses.

#### com.frankie.routes

- configureRouting.kt  
    Defines application routes and error handling.

- GET / – Health check
    
- GET /pokemon – Paginated list endpoint
    
- GET /pokemon/{name} – Single Pokémon detail  
    Also installs StatusPages for structured exception handling.

#### com.frankie.models

- PokemonApiResponse.kt  
    Data classes representing the full Pokémon object returned by the PokeAPI, including ID, name, types, and sprites.  
    
- PokemonResponse.kt  
    A simplified internal model tailored for frontend use, includes name, imageUrl, and primaryType.  
    
- PokemonListResponse.kt  
    Represents the paginated list structure returned by the API’s list endpoint. Wraps a list of basic Pokémon entries (name, url).      
    
- PokemonDetailResponse.kt  
    Defines a structured model for individual Pokémon details, used when returning full data for a single Pokémon.  
    
- toPokemonResponse()  
    Extension function that maps a PokemonApiResponse to a PokemonResponse. Used to reshape and streamline data before returning it to the client.
    

#### Tests

##### PokemonServiceTest.kt

Tests the PokemonService logic (API integration and mapping):
- getPokemon returns expected data (valid name)
- getPokemon throws on 404 or bad response (invalid name)  

##### RoutingTest.kt

Tests the HTTP endpoints using mocked services:
-  /pokemon/{name} returns correct response and status  
    Validates response body contains expected content (e.g. "pikachu")  

#### PokemonMapperTest.kt

Tests the toPokemonResponse() extension:
- Transforms full API model into simplified frontend-ready format  
    Confirms correct name, type, and sprite mapping.  

#### ApplicationTest.kt

General integration tests using testApplication {} blocks:

- / (root) returns health check text
- Custom route (/test1) behaves as expected (likely tutorial/testing setup)
