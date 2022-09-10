package com.mahmudul.movieapp.network

import com.mahmudul.movieapp.model.MovieDetail
import com.mahmudul.movieapp.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesInterface {

    @GET("?type=movie")
    suspend fun getBannerMovies(
        @Query("apikey") apiKey: String?,
        @Query("s") s: String?,
        @Query("page") page: String?
    ): Response<MovieResponse>

    @GET("?type=movie")
    suspend fun getSearchResultData(
        @Query(value = "s") searchTitle: String,
        @Query(value = "apiKey") apiKey: String,
        @Query(value = "page") pageIndex: Int
    ): Response<MovieResponse>

    @GET("?type=movie")
    suspend fun getBatmanMovies(
        @Query(value = "s") searchTitle: String,
        @Query(value = "apiKey") apiKey: String,
        @Query(value = "page") page: Int,
    ): MovieResponse

    @GET("?type=movie")
    suspend fun getLatestMovies(
        @Query(value = "s") searchTitle: String,
        @Query(value = "apiKey") apiKey: String,
        @Query(value = "type") type: String,
        @Query(value = "y") year: String,
        @Query(value = "page") page: Int,
        @Query("pageSize") pageSize: Int
    ): MovieResponse

    @GET("?plot=full")
    suspend fun getMovieDetailData(
        @Query(value = "i") movieId: String,
        @Query(value = "apiKey") apiKey: String
    ): Response<MovieDetail>
}