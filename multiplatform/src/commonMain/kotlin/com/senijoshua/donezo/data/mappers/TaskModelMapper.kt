package com.senijoshua.donezo.data.mappers

import com.senijoshua.donezo.data.local.tasks.TaskEntity
import com.senijoshua.donezo.presentation.model.PresentationTask

fun TaskEntity.toPresentation() = PresentationTask(
    id = id,
    title = title,
    description = description,
    createdTime = createdTime
)

fun List<TaskEntity>.toPresentation() = map(TaskEntity::toPresentation)
