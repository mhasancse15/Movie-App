package com.mahmudul.movieapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.mahmudul.movieapp.model.MovieResponse
import com.mahmudul.movieapp.model.Search
import com.mahmudul.movieapp.network.MoviesInterface
import com.mahmudul.movieapp.utils.Constants
import java.io.IOException


const val STARTING_INDEX = 1
class MoviePagingSource (val moviesInterface: MoviesInterface): PagingSource<Int, Search>() {
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


    /*override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        val position = params.key ?: STARTING_INDEX

        return try {
            val data = moviesInterface.getBatmanMovies(
                "Batman",
                Constants.API_KEY,
                position,
                params.loadSize
            )
            LoadResult.Page(
                data = data.Search,
                prevKey = if (params.key == STARTING_INDEX) null else position - 1,
                nextKey = if (data.Search.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { anchorPage ->
                val pageIndex = state.pages.indexOf(anchorPage)
                if (pageIndex == 0) {
                    null
                } else {
                    state.pages[pageIndex - 1].nextKey
                }
            }
        }
    }*/
}