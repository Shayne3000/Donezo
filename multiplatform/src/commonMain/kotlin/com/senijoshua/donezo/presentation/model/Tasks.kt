package com.senijoshua.donezo.presentation.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * Presentation-layer representation of a Task type
 */
data class PresentationTask(
    val id: Int,
    val title: String,
    val description: String,
    val createdTime: LocalDate,
)

/**
 * Model for encapsulating update data for a [PresentationTask]
 */
data class TaskUpdateDetails(
    val id: Int,
    val title: String,
    val description: String,
)

@OptIn(ExperimentalTime::class)
internal val tasksPreview = List(10) { index ->
    PresentationTask(
        id = index,
        title = "Check the task title $index times",
        description = "Check the task description $index times and ensure it's correct.",
        createdTime = Clock.System.todayIn(TimeZone.currentSystemDefault())
    )
}
