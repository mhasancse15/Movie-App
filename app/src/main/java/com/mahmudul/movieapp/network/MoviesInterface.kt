package com.mahmudul.movieapp.network

import com.mahmudul.movieapp.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesInterface {

    @GET("/")
    suspend fun getPopularMovies(
        @Query("apikey") apiKey: String?,
        @Query("s") s: String?,
        @Query("type") type: String?,
        @Query("page") page: String?
    ): Response<MovieResponse>
}