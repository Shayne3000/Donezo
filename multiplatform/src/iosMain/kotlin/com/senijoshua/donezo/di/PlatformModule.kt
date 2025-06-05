package com.senijoshua.donezo.di

import com.senijoshua.donezo.utils.getDatabaseBuilder
import org.koin.dsl.module

/**
 * Koin Module that declares iOS platform-specific dependencies for
 * injection.
 */
actual val platformModule = module {
    single { getDatabaseBuilder() }
}
