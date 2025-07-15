package com.senijoshua.donezo.presentation.tasks

import kotlinx.datetime.LocalDate

/**
 * Presentation-layer representation of a Task type
 */
data class TodoTasks(
    val id: String,
    val title: String,
    val description: String,
    val createdTime: LocalDate,
)
