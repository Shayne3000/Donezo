package com.senijoshua.donezo.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.senijoshua.donezo.data.local.character.CharacterDao
import com.senijoshua.donezo.data.local.character.CharacterEntity
import com.senijoshua.donezo.data.local.task.TaskDao
import com.senijoshua.donezo.data.local.task.TaskEntity

@Database(entities = [TaskEntity::class, CharacterEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class DonezoDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun characterDao(): CharacterDao

    companion object {
        const val DATABASE_NAME = "donezo_database.db"
    }
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<DonezoDatabase> {
    override fun initialize(): DonezoDatabase
}
