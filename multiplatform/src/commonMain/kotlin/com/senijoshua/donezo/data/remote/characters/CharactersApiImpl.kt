package com.senijoshua.donezo.data.remote.characters

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

private const val CHARACTERS_ENDPOINT = "characters"

class CharactersApiImpl(private val httpClient: HttpClient) : CharactersApi{
    override suspend fun getCharacters(): List<CharactersRemote> {
        return httpClient.get(CHARACTERS_ENDPOINT).body()
    }
}
