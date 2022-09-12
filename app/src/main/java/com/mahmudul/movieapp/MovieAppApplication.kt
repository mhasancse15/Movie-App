package com.mahmudul.movieapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MovieAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(object :Timber.DebugTree(){
                override fun createStackElementTag(element: StackTraceElement): String {
                    return super.createStackElementTag(element)+ ':' + element.lineNumber
                }
            })
        }
    }
}