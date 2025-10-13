package com.senijoshua.donezo.data.repository.characters

import com.senijoshua.donezo.presentation.features.characters.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getCharacters(): Flow<Result<List<Character>>>
    suspend fun getCharacterGivenId(id: Int): Result<Character>
}
