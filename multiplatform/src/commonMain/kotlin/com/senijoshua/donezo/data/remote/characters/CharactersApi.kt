package com.senijoshua.donezo.data.remote.characters

interface CharactersApi {
    suspend fun getCharacters(): List<CharactersRemote>
}
