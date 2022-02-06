package com.lobo.myjourney

import android.app.Application
import com.lobo.myjourney.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyJourneyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyJourneyApp)
            androidLogger()
            modules(
                listOf(
                    repositoryModule,
                    resourceProvider,
                    dataSourceModule,
                    useCaseModule,
                    presentationModule
                )
            )
        }
    }
}