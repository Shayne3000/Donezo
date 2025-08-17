package com.senijoshua.donezo.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.senijoshua.donezo.data.local.DonezoDatabase
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Koin module for database dependencies
 */
val persistenceModule = module {
    single<DonezoDatabase> { getRoomDatabase(get(), get(qualifier = named("IO"))) }
    single { get<DonezoDatabase>().taskDao() }
    single { get<DonezoDatabase>().characterDao() }
}

private fun getRoomDatabase(
    builder: RoomDatabase.Builder<DonezoDatabase>,
    coroutineDispatcher: CoroutineDispatcher
): DonezoDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(coroutineDispatcher)
        .build()
}
