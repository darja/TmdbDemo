package com.darja.tmdb.ui.moviedetails

import androidx.lifecycle.ViewModel
import com.darja.tmdb.api.model.Movie

class MovieDetailsViewModel: ViewModel() {
    lateinit var movie: Movie
}