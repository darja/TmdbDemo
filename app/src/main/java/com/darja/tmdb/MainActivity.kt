package com.darja.tmdb

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    val api: TmdbApi by inject()
    val schedulers: SchedulerProvider by inject()
    val bag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        api.getNowPlayingMovies()
            .with(schedulers)
            .subscribe({
                DPLog.i("%s movies playing", it.movies.size)
            }, {
                DPLog.e("Request failed")
            })
            .addTo(bag)
    }
}
