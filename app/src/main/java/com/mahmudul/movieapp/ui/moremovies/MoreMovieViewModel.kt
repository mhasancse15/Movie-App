package com.mahmudul.movieapp.ui.moremovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mahmudul.movieapp.model.Search
import com.mahmudul.movieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoreMovieViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    val getBatmanMovieList: LiveData<PagingData<Search>> = movieRepository.getBatmanMovies().cachedIn(viewModelScope)
}
