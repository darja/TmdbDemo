package com.darja.tmdb.api.model

import com.google.gson.annotations.SerializedName

class MoviesPage {
    @SerializedName("results")
    lateinit var movies: List<Movie>
}