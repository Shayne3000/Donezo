package com.senijoshua.donezo.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Koin module for provisioning Kotlin Coroutine dependencies for injection
 */
val coroutineModule = module{
    factory(qualifier = named("IO") ) { Dispatchers.IO }
}
