package com.senijoshua.donezo.data.repository.tasks

import com.senijoshua.donezo.data.local.tasks.TaskDao
import com.senijoshua.donezo.data.local.tasks.TaskEntity
import com.senijoshua.donezo.data.mappers.tasks.toPresentation
import com.senijoshua.donezo.data.utils.asResult
import com.senijoshua.donezo.presentation.model.PresentationTask
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class TasksRepositoryImpl(
    private val local: TaskDao,
    private val ioDispatcher: CoroutineDispatcher,
) : TasksRepository {
    @OptIn(ExperimentalTime::class)
    override suspend fun createTask(title: String, description: String) {
        withContext(ioDispatcher) {
            val task = TaskEntity(
                title = title,
                description = description,
                createdTime = Clock.System.todayIn(TimeZone.currentSystemDefault()),
                isComplete = false
            )

            local.insertTask(task)
        }
    }

    override fun getTasks(): Flow<Result<List<PresentationTask>>> {
        return local.getTasks()
            .map { taskEntities -> taskEntities.toPresentation() }
            .asResult()
    }

    override suspend fun toggleTaskCompleteStatus(taskId: Int) {
        withContext(ioDispatcher) {
            local.toggleTaskCompleteStatus(taskId)
        }
    }

    override fun getCompletedTasks(): Flow<Result<List<PresentationTask>>> {
        return local.getCompletedTasks()
            .map { taskEntities -> taskEntities.toPresentation() }
            .asResult()
    }

    override suspend fun updateTask(
        id: Int,
        title: String,
        description: String
    ) {
        withContext(ioDispatcher) {
            local.updateTask(id, title, description)
        }
    }

    override suspend fun deleteTask(id: Int) {
        withContext(ioDispatcher) {
            local.deleteTask(id)
        }
    }
}
