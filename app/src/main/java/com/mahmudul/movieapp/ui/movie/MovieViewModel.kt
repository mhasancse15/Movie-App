package com.mahmudul.movieapp.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.mahmudul.movieapp.model.MovieResponse
import com.mahmudul.movieapp.model.Search
import com.mahmudul.movieapp.repository.MovieRepository
import com.mahmudul.movieapp.utils.Constants
import com.mahmudul.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    private val _popularMovies = MutableLiveData<Resource<MovieResponse>>()
    val popularMovies: LiveData<Resource<MovieResponse>> = _popularMovies


    val getBatmanMovieList: LiveData<PagingData<Search>> = movieRepository.getBatmanMovies()

    fun getPopularMovies(){
        _popularMovies.postValue(Resource.LOADING())
        viewModelScope.launch {
            val response = movieRepository.getPopularMovies(Constants.API_KEY, "top", "1")
            _popularMovies.postValue(handleResponse(response))
        }
    }


    private fun handleResponse(response: Response<MovieResponse>): Resource<MovieResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return Resource.SUCCESS(it)
            }
        }
        return Resource.Error(message = response.errorBody().toString())
    }

}