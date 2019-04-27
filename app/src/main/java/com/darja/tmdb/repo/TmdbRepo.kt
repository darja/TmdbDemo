package com.darja.tmdb.repo

import com.darja.tmdb.api.TmdbApi

class TmdbRepo(val api: TmdbApi): MoviesRepo {
    override fun getNowPlayingMovies() = api.getNowPlayingMovies()

    override fun searchMovies(query: String) = api.searchMovies(query)
}