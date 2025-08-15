package com.senijoshua.donezo.data.repository

import com.senijoshua.donezo.presentation.tasks.model.TodoTask
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl : TasksRepository {
    override fun createTask() {
        TODO("Not yet implemented")
    }

    override suspend fun getTasks(): Flow<Result<TodoTask>> {
        TODO("Not yet implemented")
    }

    override fun markTaskAsComplete(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getCompletedTasks(): Flow<Result<TodoTask>> {
        TODO("Not yet implemented")
    }

    override fun updateTask(
        id: String,
        title: String,
        description: String
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteTask() {
        TODO("Not yet implemented")
    }
}
