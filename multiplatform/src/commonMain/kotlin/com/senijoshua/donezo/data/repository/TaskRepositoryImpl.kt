package com.senijoshua.donezo.data.repository

import com.senijoshua.donezo.data.local.task.TaskDao
import com.senijoshua.donezo.data.local.task.TaskEntity
import com.senijoshua.donezo.data.util.asResult
import com.senijoshua.donezo.data.util.toPresentation
import com.senijoshua.donezo.presentation.tasks.model.PresentationTask
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class TaskRepositoryImpl(
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

            local.insertOrUpdateTask(task)
        }
    }

    override fun getTasks(): Flow<Result<List<PresentationTask>>> {
        return local.getTasks()
            .map { taskEntities -> taskEntities.toPresentation() }
            .asResult()
    }

    override fun markTaskAsComplete(taskId: String) {
        TODO("Not yet implemented")
    }

    override fun getCompletedTasks(): Flow<Result<List<PresentationTask>>> {
        return local.getCompletedTasks()
            .map { taskEntities -> taskEntities.toPresentation() }
            .asResult()
    }

    override fun updateTask(
        id: Int,
        title: String,
        description: String
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteTask(id: Int) {
        TODO("Not yet implemented")
    }
}
