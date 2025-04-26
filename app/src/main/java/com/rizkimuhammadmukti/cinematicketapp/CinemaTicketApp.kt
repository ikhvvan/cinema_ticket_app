package com.rizkimuhammadmukti.cinematicketapp

import android.app.Application
import com.rizkimuhammadmukti.cinematicketapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CinemaTicketApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CinemaTicketApp)
            modules(appModule)
        }
    }
}