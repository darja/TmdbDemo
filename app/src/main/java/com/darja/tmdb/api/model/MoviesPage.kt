package com.darja.tmdb.api.model

import com.google.gson.annotations.SerializedName

class MoviesPage {
    @SerializedName("page")
    val page: Int = 0

    @SerializedName("total_pages")
    val totalPagesCount: Int = 0

    @SerializedName("results")
    lateinit var movies: List<Movie>
}