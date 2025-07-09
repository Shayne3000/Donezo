package com.senijoshua.donezo.presentation.tasks

import kotlinx.datetime.LocalDate

/**
 * Presentation-layer representation of a Task type
 */
data class ToDoTasks(
    val id: Int,
    val title: String,
    val description: String,
    val createdTime: LocalDate,
)
