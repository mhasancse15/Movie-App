package com.mahmudul.movieapp.ui.moremovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.mahmudul.movieapp.R
import com.mahmudul.movieapp.adapter.AdapterClicklListioners
import com.mahmudul.movieapp.adapter.MoviePagingAdapter
import com.mahmudul.movieapp.databinding.FragmentMoreMoviesBinding
import com.mahmudul.movieapp.databinding.FragmentMovieBinding
import com.mahmudul.movieapp.model.Search
import com.mahmudul.movieapp.ui.movie.MovieViewModel
import com.mahmudul.movieapp.utils.LogData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreMoviesFragment : Fragment() , AdapterClicklListioners {
    private lateinit var binding: FragmentMoreMoviesBinding
    val viewModel: MoreMovieViewModel by viewModels()

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
        return inflater.inflate(R.layout.fragment_more_movies, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMoreMoviesBinding.bind(view)
        binding.clickListener = this

        binding.moreMovieFragmentTopLayout.toolbarTitle.text = "More Movie"
        binding.moreMovieFragmentTopLayout.toolbarBack.setOnClickListener {
            requireActivity().onBackPressed()
        }




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


       // binding.viewModel = viewModel
    }

    override fun clickListioners(movie: Search?) {
        findNavController().navigate(
            R.id.action_moreMoviesFragment_to_movieDetailsFragment,
            bundleOf("imdbID" to movie?.imdbID.toString())
        )

        LogData(movie?.imdbID.toString())
    }
}