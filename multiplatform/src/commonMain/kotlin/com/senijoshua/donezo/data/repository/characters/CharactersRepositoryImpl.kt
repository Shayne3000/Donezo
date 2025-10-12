package com.senijoshua.donezo.data.repository.characters

import com.senijoshua.donezo.data.local.characters.CharactersDao
import com.senijoshua.donezo.data.remote.characters.CharactersApi
import kotlinx.coroutines.CoroutineDispatcher

class CharactersRepositoryImpl(
    private val localDataSource: CharactersDao,
    private val remoteDataSource: CharactersApi,
    private val ioDispatcher: CoroutineDispatcher,
) : CharactersRepository {

    override suspend fun getCharacters() {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacterGivenId() {
        TODO("Not yet implemented")
    }
}
