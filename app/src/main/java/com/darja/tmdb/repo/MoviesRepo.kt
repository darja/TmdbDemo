package com.darja.tmdb.repo

import com.darja.tmdb.api.model.MoviesPage
import io.reactivex.Single

interface MoviesRepo {
    fun getNowPlayingMovies(): Single<MoviesPage>
    fun searchMovies(query: String): Single<MoviesPage>
}