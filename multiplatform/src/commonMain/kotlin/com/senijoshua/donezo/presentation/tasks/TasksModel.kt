package com.senijoshua.donezo.presentation.tasks

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * Presentation-layer representation of a Task type
 */
data class TodoTasks(
    val id: String,
    val title: String,
    val description: String,
    val createdTime: LocalDate,
)

@OptIn(ExperimentalTime::class)
internal val previewTasks = List(10) { index ->
    TodoTasks(
        id = index.toString(),
        title = "Check the task title $index times",
        description = "Check the task description $index times and ensure it's correct.",
        createdTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    )
}
