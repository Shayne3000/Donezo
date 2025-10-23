package com.senijoshua.donezo.data.local

import androidx.room.AutoMigration
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.senijoshua.donezo.data.local.characters.CharactersDao
import com.senijoshua.donezo.data.local.characters.CharactersEntity
import com.senijoshua.donezo.data.local.tasks.TaskDao
import com.senijoshua.donezo.data.local.tasks.TaskEntity
import com.senijoshua.donezo.data.local.utils.DateConverter

@Database(
    entities = [TaskEntity::class, CharactersEntity::class],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(DateConverter::class)
abstract class DonezoDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun characterDao(): CharactersDao

    companion object {
        const val DATABASE_NAME = "donezo_database.db"
    }
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<DonezoDatabase> {
    override fun initialize(): DonezoDatabase
}
