package com.mahmudul.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mahmudul.movieapp.databinding.ListMovieBannerBinding
import com.mahmudul.movieapp.model.Search
import com.smarteist.autoimageslider.SliderViewAdapter
 class BannerSliderAdapter(
    private var bannerList: List<Search>,
     private val bannerClickListener: AdapterClicklListioners
) :
    SliderViewAdapter<BannerSliderAdapter.BannerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): BannerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListMovieBannerBinding.inflate(layoutInflater,parent,false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(bannerList[position], bannerClickListener)
    }

    override fun getCount(): Int {
        return bannerList.size
    }

    class BannerViewHolder(val binding: ListMovieBannerBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {
        fun bind(data: Search, badgesClickListener: AdapterClicklListioners) {
            binding.movie = data
            binding.clickListener = badgesClickListener
            binding.executePendingBindings()
        }
    }

}