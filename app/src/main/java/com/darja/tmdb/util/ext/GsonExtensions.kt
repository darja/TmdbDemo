package com.darja.tmdb.util.ext

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.nio.charset.Charset

inline fun <reified T> Gson.fromJsonFile(context: Context, path: String): T {
    val json = getJson(context, path)
    return this.fromJson<T>(json, object : TypeToken<T>() {}.type)
}

fun getJson(context: Context, path: String): String {
    val inputStream = context.assets.open(path)
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()
    return String(buffer, Charset.forName("UTF-8"))
}
