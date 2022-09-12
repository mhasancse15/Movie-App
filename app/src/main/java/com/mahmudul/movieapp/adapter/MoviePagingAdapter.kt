package com.mahmudul.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mahmudul.movieapp.databinding.ListMovieBinding
import com.mahmudul.movieapp.model.Search


class MoviePagingAdapter(val adapterClicklListioners: AdapterClicklListioners) :
    PagingDataAdapter<Search, MoviePagingAdapter.MyViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Search>() {
            override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }

    inner class MyViewHolder(val viewDataBinding: ListMovieBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = getItem(position)
        holder.viewDataBinding.setVariable(BR.movie, item)

        holder.viewDataBinding.rootView.setOnClickListener {
            adapterClicklListioners.clickListioners(item)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
}