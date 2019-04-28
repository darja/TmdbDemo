package com.darja.tmdb.app.di

import android.content.Context
import com.darja.tmdb.BuildConfig
import com.darja.tmdb.R
import com.darja.tmdb.api.ApiKeyInterceptor
import com.darja.tmdb.api.TmdbApi
import com.darja.tmdb.repo.MockRepo
import com.darja.tmdb.repo.TmdbRepo
import com.darja.tmdb.util.Flavors
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val api = module {
    single { NetworkBuilder.buildRetrofit(androidApplication()) }
    single { NetworkBuilder.buildApi(get())}
    single {
        when (BuildConfig.FLAVOR) {
            Flavors.MOCK -> MockRepo(get())
            else -> TmdbRepo(get())
        }
    }
}

private object NetworkBuilder {
    fun buildApi(retrofit: Retrofit): TmdbApi = retrofit.create(TmdbApi::class.java)

    fun buildRetrofit(context: Context): Retrofit {
        val gson = GsonBuilder().create()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ApiKeyInterceptor(context.getString(R.string.tmdb_api_key)))
            .build()


        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.tmdb_base_url))
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}