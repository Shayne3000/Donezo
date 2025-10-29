package com.senijoshua.donezo.presentation.model

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

internal val characterPreview = List(10) { index ->
    Character(
        id = index,
        fullName = "Character $index's full name",
        thumbnailUrl = "Character $index's thumbnail",
        title = "Character $index's title",
        family = "Character $index's family"
    )
}
