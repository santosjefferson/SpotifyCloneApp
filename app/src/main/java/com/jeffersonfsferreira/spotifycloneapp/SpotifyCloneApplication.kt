package com.jeffersonfsferreira.spotifycloneapp

import android.app.Application
import com.jeffersonfsferreira.spotifycloneapp.di.modules.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SpotifyCloneApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SpotifyCloneApplication)
            modules(appModule)
        }
    }
}