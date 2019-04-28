package com.darja.tmdb.ui.movieslist

import androidx.databinding.ObservableArrayList
import com.darja.tmdb.R
import com.darja.tmdb.api.model.Movie
import com.darja.tmdb.ui.common.DataBindingRecyclerAdapter

class MoviesAdapter(items: ObservableArrayList<Movie>): DataBindingRecyclerAdapter<Movie>(items) {
    override fun getLayoutIdForPosition(position: Int): Int = R.layout.item_movie

    override fun getItemId(position: Int): Long = position.toLong()
}