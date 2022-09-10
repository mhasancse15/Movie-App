package com.mahmudul.movieapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Search(
    val Poster: String,
    val Title: String,
    val Type: String,
    val Year: String,
    val imdbID: String
):Parcelable