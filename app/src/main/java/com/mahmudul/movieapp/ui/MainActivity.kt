package com.mahmudul.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.mahmudul.movieapp.R
import com.mahmudul.movieapp.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var titleText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppUtils.hideStatusBar(window,true)
        supportActionBar!!.hide()
    }

    override fun onBackPressed() {
            super.onBackPressed()
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navigation)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}