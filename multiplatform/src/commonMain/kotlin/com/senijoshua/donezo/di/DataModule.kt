package com.senijoshua.donezo.di

import com.senijoshua.donezo.data.repository.characters.CharactersRepository
import com.senijoshua.donezo.data.repository.characters.CharactersRepositoryImpl
import com.senijoshua.donezo.data.repository.tasks.TasksRepository
import com.senijoshua.donezo.data.repository.tasks.TasksRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin module for data layer dependencies
 */
val dataModule  = module {
    // Provision the same TasksRepositoryImpl instance when needing to
    // inject instances for the TasksRepositoryImpl or TasksRepository
    // dependencies in a class
    single {
        TasksRepositoryImpl(
            local = get(),
            ioDispatcher = get(named(IO_DISPATCHER))
        )
    } bind TasksRepository::class

    // Provision and inject the same CharactersRepositoryImpl instance
    // for CharactersRepositoryImpl and CharactersRepository dependencies
    single { CharactersRepositoryImpl(
        localDataSource = get(),
        remoteDataSource = get(),
        ioDispatcher = get(named(IO_DISPATCHER))
    ) } bind CharactersRepository::class
}
