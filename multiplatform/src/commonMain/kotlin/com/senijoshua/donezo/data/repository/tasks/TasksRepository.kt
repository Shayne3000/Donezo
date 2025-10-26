package com.senijoshua.donezo.data.repository.tasks

import com.senijoshua.donezo.presentation.model.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun createTask(title: String, description: String)
    fun getTasks(): Flow<Result<List<Task>>>
    suspend fun toggleTaskCompleteStatus(taskId: Int)
    fun getCompletedTasks(): Flow<Result<List<Task>>>
    suspend fun updateTask(id: Int, title: String, description: String)
    suspend fun deleteTask(id: Int)
}
