package com.senijoshua.donezo.data.repository.tasks

import com.senijoshua.donezo.presentation.model.PresentationTask
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun createTask(title: String, description: String)
    fun getTasks(): Flow<Result<List<PresentationTask>>>
    suspend fun toggleTaskCompleteStatus(taskId: Int)
    fun getCompletedTasks(): Flow<Result<List<PresentationTask>>>
    suspend fun updateTask(id: Int, title: String, description: String)
    suspend fun deleteTask(id: Int)
}
