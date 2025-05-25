package com.senijoshua.donezo.di

import org.koin.dsl.module

val appModule = module{
    includes() //networkModule, dataModule, persistenceModule
    single {  }
}
