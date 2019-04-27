package com.darja.tmdb.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Movie(
    @SerializedName("id") var id: Long = 0,
    @SerializedName("title") var title: String = "",
    @SerializedName("poster_path") var poster: String? = null,
    @SerializedName("backdrop_path") var backdrop: String? = null,
    @SerializedName("overview") var description: String? = null
): Parcelable