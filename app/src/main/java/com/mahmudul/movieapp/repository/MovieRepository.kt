package com.mahmudul.movieapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.mahmudul.movieapp.model.MovieDetail
import com.mahmudul.movieapp.model.MovieResponse
import com.mahmudul.movieapp.network.MoviesInterface
import com.mahmudul.movieapp.model.Search
import com.mahmudul.movieapp.paging.BatmanMoviePagingSource
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(val moviesInterface: MoviesInterface) {

    suspend fun getBannerMovies(
        apiKey: String,
        s: String,
        pageNo: String
    ): Response<MovieResponse> {
        return moviesInterface.getBannerMovies(apiKey, s, pageNo)
    }

    fun getBatmanMovies(): LiveData<PagingData<Search>> = Pager(
        config = PagingConfig(
            20,
            5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            BatmanMoviePagingSource(moviesInterface)
        }

    ).liveData

    suspend fun getMovieDetails(movieId: String, apiKey: String): Response<MovieDetail> {
        return moviesInterface.getMovieDetailData(movieId, apiKey)
    }

}