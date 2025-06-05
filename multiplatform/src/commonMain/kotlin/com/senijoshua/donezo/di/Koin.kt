package com.senijoshua.donezo.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * Function to initialise koin on target platforms
 */
fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        // This gives Koin modules access to the Context from Android
        appDeclaration()
        modules(appModule, platformModule)
    }
}

/**
 * Koin initializer for iOS since native iOS code
 * does not support functions with default values
 */
fun initKoinIos() = initKoin(appDeclaration = {})
