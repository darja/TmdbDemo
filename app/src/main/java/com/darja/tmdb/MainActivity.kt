package com.darja.tmdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.darja.tmdb.api.model.Movie
import com.darja.tmdb.ui.moviedetails.MovieDetailsFragment
import com.darja.tmdb.ui.movieslist.MoviesListFragment
import com.darja.tmdb.util.ext.replaceFragmentSafely

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            replaceFragmentSafely(MoviesListFragment(), MoviesListFragment::class.java.simpleName, containerViewId = R.id.container)
        }
    }

    fun openMovieDetails(movie: Movie) {
        replaceFragmentSafely(MovieDetailsFragment.newInstance(movie), MovieDetailsFragment::class.java.simpleName, containerViewId = R.id.container)
    }
}
