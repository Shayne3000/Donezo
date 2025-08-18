package com.senijoshua.donezo.data.repository

import com.senijoshua.donezo.presentation.tasks.model.PresentationTask
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
) : TasksRepository {
    override fun createTask() {
        TODO("Not yet implemented")
    }

    override suspend fun getTasks(): Flow<Result<PresentationTask>> {
        TODO("Not yet implemented")
    }

    override fun markTaskAsComplete(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getCompletedTasks(): Flow<Result<PresentationTask>> {
        TODO("Not yet implemented")
    }

    override fun updateTask(
        id: Int,
        title: String,
        description: String
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteTask() {
        TODO("Not yet implemented")
    }
}
