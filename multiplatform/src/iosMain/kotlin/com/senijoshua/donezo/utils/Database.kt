package com.senijoshua.donezo.utils

import androidx.room.Room
import androidx.room.RoomDatabase
import com.senijoshua.donezo.data.local.DonezoDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

/**
 * iOS platform-specific database builder that would be called from
 * common code to instantiate Room on iOS targets
 */
fun getDatabaseBuilder(): RoomDatabase.Builder<DonezoDatabase> {
    val dbFilePath = documentDirectory() + DonezoDatabase.DATABASE_NAME

    return Room.databaseBuilder<DonezoDatabase>(
        name = dbFilePath,
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
