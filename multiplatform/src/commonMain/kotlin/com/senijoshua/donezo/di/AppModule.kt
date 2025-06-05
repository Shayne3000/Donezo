package com.senijoshua.donezo.di

import org.koin.core.module.Module
import org.koin.dsl.module

val appModule = module{
    includes(persistenceModule, coroutineModule)
}

expect val platformModule: Module
