package com.senijoshua.donezo

import android.app.Application
import com.senijoshua.donezo.di.initKoin
import org.koin.android.ext.koin.androidContext

class DonezoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            appDeclaration = {
                androidContext(this@DonezoApplication)
            }
        )
    }
}
