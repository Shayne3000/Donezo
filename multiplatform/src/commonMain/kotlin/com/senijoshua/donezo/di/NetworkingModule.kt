package com.senijoshua.donezo.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

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
        // Not supplying an engine will allow the client to select during compile time,
        // the engine appropriate for the specific target platform
        // based on the supplied engine dependencies for each target in the build config file.
        HttpClient {
            install(ContentNegotiation) {
                // register kotlinx.json as the ktorClient's JSON serializer
                json(json = get<Json>(), contentType = ContentType.Application.Json)
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.BODY
                sanitizeHeader { header -> header == HttpHeaders.Authorization }
            }
        }
    }
}
