package com.mahmudul.movieapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.mahmudul.movieapp.model.MovieResponse
import com.mahmudul.movieapp.network.MoviesInterface
import com.mahmudul.movieapp.adapter.MoviePagingAdapter
import com.mahmudul.movieapp.model.Search
import com.mahmudul.movieapp.paging.MoviePagingSource
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(val moviesInterface: MoviesInterface) {

   suspend fun getPopularMovies(apiKey: String, s: String, pageNo: String): Response<MovieResponse>{
       return moviesInterface.getPopularMovies( apiKey, s, pageNo)
   }

    fun getBatmanMovies(): LiveData<PagingData<Search>> = Pager(
        config = PagingConfig(
            20,
            5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            MoviePagingSource(moviesInterface)
        }

    ).liveData


}