package com.mahmudul.movieapp.ui.details

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.ads.interactivemedia.v3.api.AdErrorEvent
import com.google.ads.interactivemedia.v3.api.AdEvent
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import com.mahmudul.movieapp.R
import com.mahmudul.movieapp.databinding.FragmentMovieDetailsBinding
import com.mahmudul.movieapp.utils.LogData
import com.mahmudul.movieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment : Fragment(){

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
        return inflater.inflate(com.mahmudul.movieapp.R.layout.fragment_movie_details, container, false)
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
                    val response = dataHandler.data
                    if (response != null){
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


        binding.playMovie.setOnClickListener{
            findNavController().navigate(
                R.id.action_movieDetailsFragment_to_videoPlayerFragment)
        }

    }


}