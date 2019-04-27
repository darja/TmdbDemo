package com.darja.tmdb.app.di

import com.darja.tmdb.util.rx.ApplicationSchedulerProvider
import com.darja.tmdb.util.rx.SchedulerProvider
import org.koin.dsl.module.module

val app = module {
    single { ApplicationSchedulerProvider() as SchedulerProvider }
}