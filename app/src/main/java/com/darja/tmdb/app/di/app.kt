package com.darja.tmdb.app.di

import com.darja.tmdb.ui.movieslist.MoviesListViewModel
import com.darja.tmdb.util.rx.ApplicationSchedulerProvider
import com.darja.tmdb.util.rx.SchedulerProvider
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val app = module {
    single { ApplicationSchedulerProvider() as SchedulerProvider }
}

val viewModels = module {
    viewModel { MoviesListViewModel(get(), get()) }
}