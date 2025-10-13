package com.senijoshua.donezo.presentation.features.characters

/***
 * Presentation-layer Character model
 */
data class Character(
    val id: Int,
    val fullName: String,
    val thumbnailUrl: String,
    val title: String,
    val family: String
)
