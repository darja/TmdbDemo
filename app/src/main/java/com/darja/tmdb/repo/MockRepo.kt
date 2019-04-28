package com.darja.tmdb.repo

import android.content.Context
import com.darja.tmdb.api.model.MoviesPage
import com.darja.tmdb.util.ext.fromJsonFile
import com.google.gson.Gson
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class MockRepo(context: Context): MoviesRepo {
    private val mockContent: MoviesPage = Gson().fromJsonFile(context, "list.json")

    override fun getNowPlayingMovies(): Single<MoviesPage> {
        return Single.timer(2, TimeUnit.SECONDS)
            .map { mockContent }
    }

    override fun searchMovies(query: String): Single<MoviesPage> {
        return Single.timer(2, TimeUnit.SECONDS)
            .map { val page = MoviesPage()
                page.movies = mockContent.movies.filter { it.title.contains(query, ignoreCase = true) }
                page
            }
    }
}