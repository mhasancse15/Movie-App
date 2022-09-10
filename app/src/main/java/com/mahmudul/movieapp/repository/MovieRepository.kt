package com.mahmudul.movieapp.repository

import com.mahmudul.movieapp.model.MovieResponse
import com.mahmudul.movieapp.network.MoviesInterface
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(val moviesInterface: MoviesInterface) {

   suspend fun getPopularMovies(apiKey: String, s: String, type: String, pageNo: String): Response<MovieResponse>{
       return moviesInterface.getPopularMovies( apiKey, s, type, pageNo)
   }


}