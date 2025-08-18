package com.senijoshua.donezo.data.repository

import com.senijoshua.donezo.presentation.tasks.model.PresentationTask
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun createTask()
    suspend fun getTasks(): Flow<Result<PresentationTask>>
    fun markTaskAsComplete(taskId: String)
    suspend fun getCompletedTasks(): Flow<Result<PresentationTask>>
    fun updateTask(id: Int, title: String, description: String)
    fun deleteTask()
}
