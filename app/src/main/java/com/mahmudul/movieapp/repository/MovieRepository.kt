package com.mahmudul.movieapp.repository

import com.mahmudul.movieapp.network.ApiHelper
import com.mahmudul.movieapp.network.ApiService
import javax.inject.Inject

class MovieRepository @Inject constructor(val apiService: ApiService, private val apiHelper: ApiHelper) {


}