package com.senijoshua.donezo.di

import org.koin.core.module.Module
import org.koin.dsl.module

val multiplatformModule = module {
    includes(presentationModule, persistenceModule, networkingModule, coroutineModule)
}

expect val platformModule: Module
