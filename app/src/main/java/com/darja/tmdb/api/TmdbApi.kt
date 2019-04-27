package com.darja.tmdb.api

import com.darja.tmdb.api.model.MoviesPage
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/now_playing")
    fun getNowPlayingMovies(): Single<MoviesPage>

    @GET("search/movie")
    fun searchMovies(@Query("query") query: String): Single<MoviesPage>
}