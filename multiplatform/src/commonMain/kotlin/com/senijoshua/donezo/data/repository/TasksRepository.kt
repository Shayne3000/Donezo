package com.senijoshua.donezo.data.repository

import com.senijoshua.donezo.presentation.tasks.model.PresentationTask
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun createTask(title: String, description: String)
    fun getTasks(): Flow<Result<List<PresentationTask>>>
    fun markTaskAsComplete(taskId: String)
    fun getCompletedTasks(): Flow<Result<List<PresentationTask>>>
    fun updateTask(id: Int, title: String, description: String)
    fun deleteTask(id: Int)
}
