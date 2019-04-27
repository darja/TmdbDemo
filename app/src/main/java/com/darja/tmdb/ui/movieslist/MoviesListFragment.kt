package com.darja.tmdb.ui.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.darja.tmdb.MainActivity
import com.darja.tmdb.R
import com.darja.tmdb.databinding.FragmentMoviesListBinding
import kotlinx.android.synthetic.main.fragment_movies_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesListFragment: Fragment() {
    val viewModel: MoviesListViewModel by viewModel()

    private val adapter: MoviesAdapter by lazy {
        MoviesAdapter(viewModel.movies)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentMoviesListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_list, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.movies.isEmpty()) {
            viewModel.requestMovies()
        }

        observeViewModel()
        setupMoviesGrid()
        setupToolbar()
    }

    private fun observeViewModel() {
        viewModel.error.observe(this, Observer { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.isLoading.observe(this, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    private fun setupMoviesGrid() {
        adapter.onClickListener = { movie, _ ->
            (activity as MainActivity).openMovieDetails(movie)
        }

        moviesGrid.layoutManager = GridLayoutManager(context, 2)
        moviesGrid.adapter = adapter
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).run {
            setSupportActionBar(toolbar)
        }
    }
}