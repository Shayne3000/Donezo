package com.senijoshua.donezo.data.repository

import com.senijoshua.donezo.presentation.tasks.model.TodoTask
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun createTask()
    suspend fun getTasks(): Flow<Result<TodoTask>>
    fun markTaskAsComplete(taskId: String)
    suspend fun getCompletedTasks(): Flow<Result<TodoTask>>
    fun updateTask(id: String, title: String, description: String)
    fun deleteTask()
}
