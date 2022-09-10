package com.mahmudul.movieapp.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mahmudul.movieapp.R
import com.mahmudul.movieapp.databinding.FragmentMovieBinding
import com.mahmudul.movieapp.utils.LogData
import com.mahmudul.movieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieBinding.bind(view)
        viewModel.popularMovies.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {

                is Resource.SUCCESS -> {
                    LogData("onViewCreated: SUCCESS " + dataHandler.message)
                }
                is Resource.Error -> {
                    LogData("onViewCreated: Error " + dataHandler.message)
                }
                is Resource.LOADING -> {
                    LogData("onViewCreated: LOADING..")

                }
            }
        }

        viewModel.getPopularMovies()

    }

}