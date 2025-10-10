package com.senijoshua.donezo.data.remote.characters

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersRemote(
    val id: Int,
    val fullName: String,
    @SerialName("imageUrl")
    val thumbnailUrl: String,
    val title: String,
    val family: String
)
