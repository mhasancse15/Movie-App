package com.mahmudul.movieapp.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Response")
    var Response: String,

    @SerializedName("Search")
    var Search: List<Search>,

    @SerializedName("totalResults")
    var totalResults: String
)