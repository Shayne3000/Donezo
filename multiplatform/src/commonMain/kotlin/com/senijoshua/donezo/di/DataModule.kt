package com.senijoshua.donezo.di

import com.senijoshua.donezo.data.repository.TaskRepositoryImpl
import com.senijoshua.donezo.data.repository.TasksRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin module for data layer dependencies
 */
val dataModule  = module {
    singleOf(::TaskRepositoryImpl) bind TasksRepository::class
}
