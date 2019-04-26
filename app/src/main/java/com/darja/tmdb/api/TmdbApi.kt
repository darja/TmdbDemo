package com.darja.tmdb.api

import com.darja.tmdb.api.model.ApiMoviesPage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/now_playing")
    fun getNowPlayingMovies(): Call<ApiMoviesPage>

    @GET("search/movie")
    fun searchMovies(@Query("query") query: String, @Query("page") page: Int = 1): Call<ApiMoviesPage>
}