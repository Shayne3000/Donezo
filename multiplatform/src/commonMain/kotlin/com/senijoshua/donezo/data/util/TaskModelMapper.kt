package com.senijoshua.donezo.data.util

import com.senijoshua.donezo.data.local.task.TaskEntity
import com.senijoshua.donezo.presentation.tasks.model.PresentationTask

fun TaskEntity.toPresentation() = PresentationTask(
    id = id,
    title = title,
    description = description,
    createdTime = createdTime
)

fun List<TaskEntity>.toPresentation() = map(TaskEntity::toPresentation)
