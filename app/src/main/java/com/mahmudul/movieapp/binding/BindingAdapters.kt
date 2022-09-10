package com.mahmudul.movieapp.binding

import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.request.ErrorResult
import coil.request.ImageRequest
import com.mahmudul.movieapp.R
import com.mahmudul.movieapp.adapter.AdapterClicklListioners
import com.mahmudul.movieapp.adapter.BannerSliderAdapter
import com.mahmudul.movieapp.model.MovieResponse
import com.mahmudul.movieapp.model.Search
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import java.util.*


@BindingAdapter("bind:loadImage")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrBlank()) {
        view.load(url) {
            listener(
                onSuccess = { _, _ ->
                },
                onError = { request: ImageRequest, throwable: ErrorResult ->
                    request.error
                    view.load(R.drawable.no_poster)
                })
        }
    } else {
        view.load(R.drawable.no_poster)
    }
}



@BindingAdapter(value = ["bind:slideBannerResponse", "bind:bannerClickListener"])
fun slideBanner(
    sliderView: SliderView,
    bannerResponse: MovieResponse?,
    bannerClickListener: AdapterClicklListioners?
) {
    if (bannerResponse != null && !bannerResponse.Search.isNullOrEmpty()) {

        val adapter = BannerSliderAdapter(
            bannerResponse.Search as ArrayList<Search>,
            bannerClickListener!!
        )
        sliderView.setSliderAdapter(adapter)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!

        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        sliderView.indicatorSelectedColor = Color.WHITE
        sliderView.indicatorUnselectedColor = Color.GRAY
        sliderView.scrollTimeInSec = 4 //set scroll delay in seconds :
        sliderView.startAutoCycle()

    }
}







