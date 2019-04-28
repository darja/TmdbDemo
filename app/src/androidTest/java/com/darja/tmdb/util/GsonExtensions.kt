package com.darja.tmdb.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.Single
import java.nio.charset.Charset

inline fun <reified T> Gson.fromJsonFile(context: Context, path: String): Single<T> {
    val json = getJson(context, path)
    val body = this.fromJson<T>(json, object : TypeToken<T>() {}.type)
    return Single.fromObservable(Observable.fromArray(body))
}

fun getJson(context: Context, path: String): String {
    val inputStream = context.assets.open(path)
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()
    return String(buffer, Charset.forName("UTF-8"))
}
