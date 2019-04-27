package com.darja.tmdb.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.darja.tmdb.R
import com.darja.tmdb.api.model.Movie
import kotlinx.android.synthetic.main.fragment_movies_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailsFragment: Fragment() {
    val viewModel: MovieDetailsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: com.darja.tmdb.databinding.FragmentMovieDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.movie = arguments?.get(ARG_MOVIE) as Movie

        setupToolbar()
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).run {
            setSupportActionBar(toolbar)

            supportActionBar?.run {
                setDisplayShowHomeEnabled(true)
                setDisplayHomeAsUpEnabled(true)
            }

            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    companion object {
        private const val ARG_MOVIE = "movie"

        fun newInstance(movie: Movie) : MovieDetailsFragment {
            val args = Bundle()
            args.putParcelable(ARG_MOVIE, movie)

            val fragment = MovieDetailsFragment()
            fragment.arguments = args

            return fragment
        }
    }
}