package com.mahmudul.movieapp.ui.moremovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.mahmudul.movieapp.R
import com.mahmudul.movieapp.adapter.AdapterClicklListioners
import com.mahmudul.movieapp.adapter.MoviePagingAdapter
import com.mahmudul.movieapp.databinding.FragmentMoreMoviesBinding
import com.mahmudul.movieapp.model.Search
import com.mahmudul.movieapp.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MoreMoviesFragment : Fragment(R.layout.fragment_more_movies), AdapterClicklListioners {

    private var binding: FragmentMoreMoviesBinding by autoCleared()
    val viewModel: MoreMovieViewModel by viewModels()
    private lateinit var batmanMovieAdapter: MoviePagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMoreMoviesBinding.bind(view)
        batmanMovieAdapter = MoviePagingAdapter(this)
        binding.clickListener = this


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
        binding.moreMoviePreViewRecycler.adapter = batmanMovieAdapter

    }

    override fun clickListioners(movie: Search?) {
        findNavController().navigate(
            R.id.action_moreMoviesFragment_to_movieDetailsFragment,
            bundleOf("imdbID" to movie?.imdbID.toString())
        )

        Timber.d(movie?.imdbID.toString())
    }
}