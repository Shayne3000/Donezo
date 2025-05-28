package com.senijoshua.donezo.utils

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.senijoshua.donezo.data.local.DonezoDatabase

/**
 * Android platform-specific database builder for instantiating Room on Android targets
 */
fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<DonezoDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(DonezoDatabase.DATABASE_NAME)

    return Room.databaseBuilder<DonezoDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
