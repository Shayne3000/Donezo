package com.senijoshua.donezo.di

import com.senijoshua.donezo.data.remote.characters.CharactersApi
import com.senijoshua.donezo.data.remote.characters.CharactersApiImpl
import com.senijoshua.donezo.utils.isDebugBuild
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.bind
import org.koin.dsl.module

private const val BASE_URL = "https://thronesapi.com/api/v2/"

/**
 * Koin module for networking dependencies
 */
val networkingModule = module {
    // declare Kotlinx.json JSON serializer
    single<Json> {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }

    // declare ktor client instance
    single<HttpClient> {
        // Not supplying an engine will allow the client to select
        // the engine appropriate for the specific target platform during compile time
        // based on the supplied engine dependencies for each target in the build config file.
        HttpClient {
            // setup default response validation for catching non-2xx responses
            expectSuccess = true
            install(ContentNegotiation) {
                // register kotlinx.json as the ktorClient's JSON serializer
                json(json = get<Json>(), contentType = ContentType.Application.Json)
            }
            defaultRequest {
                url(BASE_URL)
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = if (isDebugBuild()) LogLevel.BODY else LogLevel.NONE
                sanitizeHeader { header -> header == HttpHeaders.Authorization }
            }
        }
    }

    single { CharactersApiImpl(httpClient = get()) } bind CharactersApi::class
}
