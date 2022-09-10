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

    private lateinit var movieAdapter: MoviePagingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieAdapter = MoviePagingAdapter(this)

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
        recyclerviewItemScreen()

        viewModel.getBatmanMovieList.observe(viewLifecycleOwner) {
            movieAdapter.submitData(lifecycle, it)
        }

        movieAdapter.addLoadStateListener { state ->

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
        binding.movieRecyclerView.adapter = movieAdapter


        viewModel.popularMovies.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {

                is Resource.SUCCESS -> {
                    Log.d("API", dataHandler.data?.Search?.size.toString())
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

    override fun clickListioners(article: Search?) {
       // TODO("Not yet implemented")
    }


}