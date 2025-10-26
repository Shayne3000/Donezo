package com.senijoshua.donezo.data.mappers.characters

import com.senijoshua.donezo.data.local.characters.CharactersEntity
import com.senijoshua.donezo.data.remote.characters.CharactersRemote
import com.senijoshua.donezo.presentation.model.Character

fun CharactersRemote.toLocal() = CharactersEntity(
    id = id,
    fullName = fullName,
    thumbnailUrl = thumbnailUrl,
    title = title,
    family = family
)

fun List<CharactersRemote>.toLocal() = map(CharactersRemote::toLocal)

fun CharactersEntity.toPresentation() = Character(
    id = id,
    fullName = fullName,
    thumbnailUrl = thumbnailUrl,
    title = title,
    family = family
)

fun List<CharactersEntity>.toPresentation() = map(CharactersEntity::toPresentation)
