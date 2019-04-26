package com.darja.tmdb

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    val api: TmdbApi by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        api.getNowPlayingMovies().enqueue(object: Callback<ApiMoviesPage> {
            override fun onFailure(call: Call<ApiMoviesPage>, t: Throwable) {
                DPLog.e("Request failed")
            }

            override fun onResponse(call: Call<ApiMoviesPage>, response: Response<ApiMoviesPage>) {
                if (response.isSuccessful) {
                    DPLog.i("%s movies playing", response.body()?.movies?.size)
                } else {
                    DPLog.e("Bad response", response.errorBody())
                }
            }

        })
    }
}
