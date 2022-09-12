package com.mahmudul.movieapp.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mahmudul.movieapp.R
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


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


object AppUtils {
    fun hideStatusBar(window: Window, darkText: Boolean) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        var flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        if (darkText) {
            flag = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        window.decorView.systemUiVisibility = flag or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    fun hideStatusBar(darkText: Boolean, activity: Activity) {
        hideStatusBar(activity.window, darkText)
    }


     fun statusBarHeightMeasurement(
        window: Window,
        activity: Activity,
        view: ConstraintLayout
    ) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               // hideStatusBar(window, false)
                var result = 0
                val resourceId =
                    activity.resources.getIdentifier("status_bar_height", "dimen", "android")
                if (resourceId > 0) {
                    result = activity.resources.getDimensionPixelSize(resourceId) + 2
                }
                val layoutParamsLayoutInfo: ConstraintLayout.LayoutParams =
                    view.layoutParams as ConstraintLayout.LayoutParams
                layoutParamsLayoutInfo.setMargins(0, result - 20, 0, 0)
                view.layoutParams = layoutParamsLayoutInfo

            }
        } catch (e: Exception) {
            e.message
        }
    }
}