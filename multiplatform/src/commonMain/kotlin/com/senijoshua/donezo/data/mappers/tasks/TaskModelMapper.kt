package com.senijoshua.donezo.data.mappers.tasks

import com.senijoshua.donezo.data.local.tasks.TaskEntity
import com.senijoshua.donezo.presentation.model.Task

fun TaskEntity.toPresentation() = Task(
    id = id,
    title = title,
    description = description,
    createdTime = createdTime
)

fun List<TaskEntity>.toPresentation() = map(TaskEntity::toPresentation)
