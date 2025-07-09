package com.senijoshua.donezo.di

import com.senijoshua.donezo.presentation.tasks.TasksViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::TasksViewModel)
}
