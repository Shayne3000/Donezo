package com.senijoshua.donezo.di

import com.senijoshua.donezo.data.local.DonezoDatabase
import com.senijoshua.donezo.data.local.getRoomDatabase
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
