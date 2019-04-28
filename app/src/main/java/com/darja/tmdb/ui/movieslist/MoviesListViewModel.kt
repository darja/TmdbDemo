package com.darja.tmdb.ui.movieslist

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.darja.tmdb.api.model.Movie
import com.darja.tmdb.repo.MoviesRepo
import com.darja.tmdb.util.ext.addTo
import com.darja.tmdb.util.ext.with
import com.darja.tmdb.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MoviesListViewModel(private val repo: MoviesRepo,
                          private val schedulers: SchedulerProvider): ViewModel() {
    private val isLoadingData = MutableLiveData<Boolean>()
    private val errorData = MutableLiveData<String>()
    private val isResultEmptyData = MutableLiveData<Boolean>()

        private val bag = CompositeDisposable()

    val movies = ObservableArrayList<Movie>()
    val isLoading: LiveData<Boolean> = isLoadingData
    val isResultEmpty: LiveData<Boolean> = isResultEmptyData
    val error: LiveData<String> = errorData

    fun requestMovies() {
        isLoadingData.postValue(true)
        isResultEmptyData.postValue(false)
        repo.getNowPlayingMovies()
            .with(schedulers)
            .subscribe({
                movies.clear()
                movies.addAll(it.movies)
                isLoadingData.postValue(false)
                isResultEmptyData.postValue(movies.isEmpty())

            }, {
                isLoadingData.postValue(false)
                errorData.postValue(it.message)
            })
            .addTo(bag)
    }

    fun searchMovies(query: String) {
        isLoadingData.postValue(true)
        isResultEmptyData.postValue(false)

        repo.searchMovies(query)
            .with(schedulers)
            .subscribe({
                movies.clear()
                movies.addAll(it.movies)
                isLoadingData.postValue(false)
                isResultEmptyData.postValue(movies.isEmpty())
            }, {
                isLoadingData.postValue(false)
                errorData.postValue(it.message)
            })
            .addTo(bag)
    }
}