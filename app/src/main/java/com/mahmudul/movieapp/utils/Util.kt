package com.mahmudul.movieapp.utils

import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mahmudul.movieapp.R


fun ImageView.loadImageFromGlide(url: String?) {
    if (url != null) {
        Glide.with(this)
            .load(url)
            .error(R.drawable.ic_launcher_background)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }

}

fun Fragment.LogData(message: String) {
    Log.d(this.javaClass.simpleName, "Log -->: " + message)
}

