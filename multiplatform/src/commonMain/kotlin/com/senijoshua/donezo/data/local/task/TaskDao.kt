package com.senijoshua.donezo.data.local.task

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface TaskDao {
    @Upsert
    suspend fun insertOrUpdateTask(task: TaskEntity)
}
