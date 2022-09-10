package com.mahmudul.movieapp.ui.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mahmudul.movieapp.R
import com.mahmudul.movieapp.databinding.FragmentMovieDetailsBinding
import com.mahmudul.movieapp.model.MovieResponse
import com.mahmudul.movieapp.model.Search
import com.mahmudul.movieapp.utils.LogData
import com.mahmudul.movieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    val viewModel: MovieDetailsViewModel by viewModels()


    // get the arguments from the Registration fragment
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieDetailsBinding.bind(view)

        val data = args.imdbID
        LogData(data.toString())



        viewModel.movieDetailsResponse.observe(viewLifecycleOwner) { dataHandler ->
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

        viewModel.getMovieDetailsData(data.toString())

    }


}