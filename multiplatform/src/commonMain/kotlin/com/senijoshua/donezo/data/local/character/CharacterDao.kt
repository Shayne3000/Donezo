package com.senijoshua.donezo.data.local.character

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface CharacterDao {
    @Upsert
    suspend fun insertOrUpdateCharacter(character: CharacterEntity)
}
