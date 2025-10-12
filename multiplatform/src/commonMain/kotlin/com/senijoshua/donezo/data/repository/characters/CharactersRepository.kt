package com.senijoshua.donezo.data.repository.characters

interface CharactersRepository {
    suspend fun getCharacters()
    suspend fun getCharacterGivenId()
}
