package com.senijoshua.donezo.data.local.task

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun insertOrUpdateTask(task: TaskEntity)

    @Query("SELECT * FROM tasks")
    fun getTasks(): Flow<List<TaskEntity>> // Room executes Flow queries on a background thread automatically

    @Query("SELECT * FROM tasks WHERE isComplete = 1")
    fun getCompletedTasks(): Flow<List<TaskEntity>>

    @Query("UPDATE tasks SET isComplete = CASE WHEN isComplete = 1 THEN 0 ELSE 1 END WHERE id = :taskId")
    suspend fun toggleTaskCompleteStatus(taskId: String)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)
}
