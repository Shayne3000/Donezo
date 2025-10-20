package com.senijoshua.donezo.data.repository.characters

import com.senijoshua.donezo.data.local.characters.CharactersDao
import com.senijoshua.donezo.data.mappers.characters.toLocal
import com.senijoshua.donezo.data.mappers.characters.toPresentation
import com.senijoshua.donezo.data.remote.characters.CharactersApi
import com.senijoshua.donezo.data.utils.asResult
import com.senijoshua.donezo.presentation.features.characters.Character
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class CharactersRepositoryImpl(
    private val localDataSource: CharactersDao,
    private val remoteDataSource: CharactersApi,
    private val ioDispatcher: CoroutineDispatcher,
) : CharactersRepository {

    override fun getCharacters(): Flow<Result<List<Character>>> {
       return localDataSource.getCharacters()
           .map { charactersEntities ->
               charactersEntities.toPresentation()
           }
           .onEach { characters ->
               if (characters.isEmpty()) {
                   // NB: This only executes on first load.
                   // Ideally, we would invalidate the cache after a while but
                   // given that this is just a POC, we're not doing that.
                   val result = remoteDataSource.getCharacters()
                   localDataSource.upsertCharacters(result.toLocal())
               }
           }
           .flowOn(ioDispatcher)
           .asResult()
    }

    override suspend fun getCharacterGivenId(id: Int): Result<Character> {
        return withContext(ioDispatcher) {
            try {
                val character = localDataSource.getCharacterGivenId(id).toPresentation()
                Result.success(character)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
