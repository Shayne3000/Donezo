package com.senijoshua.donezo.data.local.characters

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharactersEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String,
    val title: String,
    val family: String
)
