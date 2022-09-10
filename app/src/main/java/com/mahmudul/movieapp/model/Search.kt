package com.mahmudul.movieapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Search(
    @SerializedName("Type")
    var type: String,

    @SerializedName("Year")
    var year: String,

    @SerializedName("imdbID")
    var imdbID: String,

    @SerializedName("Poster")
    var poster: String,

    @SerializedName("Title")
    var title: String
):Parcelable