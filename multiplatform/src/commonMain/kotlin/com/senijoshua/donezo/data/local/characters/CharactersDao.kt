package com.senijoshua.donezo.data.local.characters

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {
    @Upsert
    suspend fun upsertCharacters(characters: List<CharactersEntity>)

    @Query("SELECT * FROM characters")
    fun getCharacters(): Flow<List<CharactersEntity>>

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacterGivenId(id: Int): CharactersEntity
}
