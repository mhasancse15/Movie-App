package com.mahmudul.movieapp.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mahmudul.movieapp.R
import com.mahmudul.movieapp.databinding.FragmentMovieDetailsBinding
import com.mahmudul.movieapp.utils.LogData
import com.mahmudul.movieapp.utils.Resource
import com.mahmudul.movieapp.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private var binding: FragmentMovieDetailsBinding by autoCleared()
    val viewModel: MovieDetailsViewModel by viewModels()
    // get the arguments from the Registration fragment
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieDetailsBinding.bind(view)
        val data = args.imdbID
        Timber.d(data.toString())

        viewModel.movieDetailsResponse.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {

                is Resource.SUCCESS -> {
                    LogData("onViewCreated: SUCCESS " + dataHandler.message)
                    val response = dataHandler.data
                    if (response != null) {
                        viewModel.movieDetailsModel = response
                        binding.viewModel = viewModel.movieDetailsModel
                    }
                }
                is Resource.Error -> {
                    LogData("onViewCreated: Error " + dataHandler.message)
                }
                is Resource.LOADING -> {
                    LogData("onViewCreated: LOADING..")

                }
            }
        }
        viewModel.getMovieDetailsData(data.toString())

        binding.playMovie.setOnClickListener {
            findNavController().navigate(
                R.id.action_movieDetailsFragment_to_videoPlayerFragment
            )
        }
    }
}