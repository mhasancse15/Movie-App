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

    lateinit var bannerData: MutableLiveData<Resource<MovieResponse>>
    var bannerResponseData: MovieResponse? = null

    val getBatmanMovieList: LiveData<PagingData<Search>> = movieRepository.getBatmanMovies()

    fun getBannerList(): LiveData<Resource<MovieResponse>> {
        bannerData = MutableLiveData<Resource<MovieResponse>>()

        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
            bannerData.postValue(Resource.LOADING())
                movieRepository.getBannerMovies(Constants.API_KEY, "top", "1").let {
                    if (it.isSuccessful) {
                        bannerData.postValue(handleResponse(it))
                        bannerResponseData=it.body()
                    } else bannerData.postValue(Resource.Error(it.code().toString()))

            }
        }
        return bannerData
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

