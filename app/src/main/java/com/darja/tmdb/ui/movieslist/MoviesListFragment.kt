package com.darja.tmdb.ui.movieslist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.darja.tmdb.MainActivity
import com.darja.tmdb.R
import com.darja.tmdb.databinding.FragmentMoviesListBinding
import com.darja.tmdb.util.DPLog
import com.darja.tmdb.util.ScreenUtil
import kotlinx.android.synthetic.main.fragment_movies_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesListFragment: Fragment() {
    val viewModel: MoviesListViewModel by viewModel()

    private lateinit var searchView: SearchView
    private lateinit var searchMenuItem: MenuItem

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.movies_list, menu)
        setupSearchView(menu)
    }

    private fun setupSearchView(menu: Menu) {
        searchMenuItem = menu.findItem(R.id.action_search) ?: return
        searchView = searchMenuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchMovies(query)
                ScreenUtil.hideSoftKeyboard(activity)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        searchMenuItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                DPLog.checkpoint()
                onSearchQueryClosed()
                return true
            }
        })
    }

    private fun onSearchQueryClosed() {
        viewModel.requestMovies()
        ScreenUtil.hideSoftKeyboard(activity)
    }
}