package com.senijoshua.donezo.di

import com.senijoshua.donezo.data.repository.tasks.TaskRepositoryImpl
import com.senijoshua.donezo.data.repository.tasks.TasksRepository
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin module for data layer dependencies
 */
val dataModule  = module {
    single {
        TaskRepositoryImpl(
            local = get(),
            ioDispatcher = get(named("IO"))
        )
    } bind TasksRepository::class
}
