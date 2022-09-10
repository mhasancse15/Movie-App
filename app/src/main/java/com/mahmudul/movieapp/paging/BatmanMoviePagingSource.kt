package com.mahmudul.movieapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mahmudul.movieapp.model.MovieResponse
import com.mahmudul.movieapp.model.Search
import com.mahmudul.movieapp.network.MoviesInterface
import com.mahmudul.movieapp.utils.Constants


class BatmanMoviePagingSource (val moviesInterface: MoviesInterface): PagingSource<Int, Search>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        return try {

            val pageNumber = params.key ?: 1 // current page that is being displayed
            val response: MovieResponse = moviesInterface.getBatmanMovies(
                Constants.movieFilterBatman,
                Constants.API_KEY,
                pageNumber

            )

            val data :ArrayList<Search> =
                response.Search as ArrayList<Search> // List of results from the API

            LoadResult.Page(
                data = data, // Provide the List<CharacterDTO>
                prevKey = null,
                nextKey = if (data.isEmpty()) null else pageNumber + 1 // As the user scrolls provide the next page number till there is no more data
            )
        } catch (e: Exception) {
            LoadResult.Error(e)

        }
    }

    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

}