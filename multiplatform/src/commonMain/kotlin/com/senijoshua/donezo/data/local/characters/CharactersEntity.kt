package com.senijoshua.donezo.data.local.characters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharactersEntity(
    @PrimaryKey
    val id: Int
)
