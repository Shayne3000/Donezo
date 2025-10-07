package com.senijoshua.donezo.data.local.characters

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface CharactersDao {
    @Upsert
    suspend fun insertOrUpdateCharacters(character: List<CharactersEntity>)
}
