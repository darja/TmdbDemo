package com.darja.tmdb.app

import android.app.Application
import com.darja.tmdb.app.di.api
import com.darja.tmdb.app.di.app
import org.koin.android.ext.android.startKoin

class TmdbApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(app, api))
    }
}