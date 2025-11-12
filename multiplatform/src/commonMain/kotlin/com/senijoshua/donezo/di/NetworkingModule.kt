package com.senijoshua.donezo.di

import com.senijoshua.donezo.data.remote.characters.CharactersApi
import com.senijoshua.donezo.data.remote.characters.CharactersApiImpl
import com.senijoshua.donezo.utils.isDebugBuild
import io.ktor.client.HttpClient
import io.ktor.client.plugins.api.Send
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
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
            install(ContentNegotiation) {
                // register kotlinx.json as the ktorClient's JSON serializer
                json(json = get<Json>())
            }
            defaultRequest {
                url(BASE_URL)
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("**Ktor Client** \n$message")
                    }
                }
                level = if (isDebugBuild()) LogLevel.ALL else LogLevel.NONE
                sanitizeHeader { header -> header == HttpHeaders.Authorization }
            }
            install(DefaultUTF8HeaderRemoval)
        }
    }

    single { CharactersApiImpl(httpClient = get()) } bind CharactersApi::class
}

/**
 * A custom Ktor plugin that removes the `Accept-Charset: UTF-8` header
 * that the Ktor client may add to requests when using certain
 * configurations (e.g. ContentNegotiation plugin with the application/json
 * default).
 *
 * This header can cause issues with some backend services (especially
 * those using Cloudflare for security), as they might reject requests
 * containing said header and return a 403 error.
 */
private val DefaultUTF8HeaderRemoval = createClientPlugin("DefaultKtorHeaderRemoval") {
    on(Send) { request ->
        request.headers.remove("Accept-Charset")
        this.proceed(request)
    }
}
