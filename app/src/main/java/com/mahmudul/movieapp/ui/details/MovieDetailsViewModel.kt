package com.mahmudul.movieapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.mahmudul.movieapp.model.MovieDetail
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
class MovieDetailsViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    private val _movieDetailsResponse = MutableLiveData<Resource<MovieDetail>>()
    var movieDetailsResponse: LiveData<Resource<MovieDetail>> = _movieDetailsResponse

    fun getMovieDetailsData(movieId: String){
        _movieDetailsResponse.postValue(Resource.LOADING())
        viewModelScope.launch {
            val response = movieRepository.getMovieDetails(movieId,Constants.API_KEY)
            _movieDetailsResponse.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<MovieDetail>): Resource<MovieDetail> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return Resource.SUCCESS(it)
            }
        }
        return Resource.Error(message = response.errorBody().toString())
    }

}

