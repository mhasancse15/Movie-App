package com.mahmudul.movieapp.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahmudul.movieapp.R
import com.mahmudul.movieapp.adapter.AdapterClicklListioners
import com.mahmudul.movieapp.adapter.MoviePagingAdapter
import com.mahmudul.movieapp.databinding.FragmentMovieBinding
import com.mahmudul.movieapp.model.Search
import com.mahmudul.movieapp.utils.LogData
import com.mahmudul.movieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieFragment : Fragment(), AdapterClicklListioners {

    private lateinit var binding: FragmentMovieBinding
    val viewModel: MovieViewModel by viewModels()

    private lateinit var batmanMovieAdapter: MoviePagingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        batmanMovieAdapter = MoviePagingAdapter(this)

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
        binding.clickListener = this
        recyclerviewItemScreen()

        viewModel.getBatmanMovieList.observe(viewLifecycleOwner) {
            batmanMovieAdapter.submitData(lifecycle, it)
        }

        batmanMovieAdapter.addLoadStateListener { state ->

            when (state.refresh) {
                is LoadState.Loading -> {
                    binding.movieProgress.visibility = View.VISIBLE
                }
                is LoadState.NotLoading -> {
                    binding.movieProgress.visibility = View.GONE
                }
                is LoadState.Error -> {
                    binding.movieProgress.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error Occured", Toast.LENGTH_SHORT).show()
                }

            }

        }
        binding.movieRecyclerView.adapter = batmanMovieAdapter


        this.viewModel.getBannerList().observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {

                is Resource.SUCCESS -> {
                    Log.d("API", dataHandler.data?.Search?.size.toString())
                    LogData("onViewCreated: SUCCESS " + dataHandler.message)
                    binding.viewModel = viewModel
                }
                is Resource.Error -> {
                    LogData("onViewCreated: Error " + dataHandler.message)
                }
                is Resource.LOADING -> {
                    LogData("onViewCreated: LOADING..")

                }
            }
        }

    }

    private fun recyclerviewItemScreen() {
        binding.movieRecyclerView.layoutManager = object : LinearLayoutManager(
            context,
            HORIZONTAL, false
        ) {
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                lp.width = (width / 3.2).toInt()
                return true
            }
        }

    }

    override fun clickListioners(movie: Search?) {
        findNavController().navigate(
            R.id.action_movieFragment_to_movieDetailsFragment,
            bundleOf("imdbID" to movie?.imdbID.toString())
        )

        LogData(movie?.imdbID.toString())
    }


}