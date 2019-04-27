package com.darja.tmdb.api.model

import com.google.gson.annotations.SerializedName

class Movie {
    @SerializedName("id")
    var id: Long = 0

    @SerializedName("title")
    lateinit var title: String

    @SerializedName("poster_path")
    var thumbnail: String? = null

    @SerializedName("overview")
    var description: String? = null
}