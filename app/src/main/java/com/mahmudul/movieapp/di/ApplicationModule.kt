package com.mahmudul.movieapp.di

import com.mahmudul.movieapp.BuildConfig
import com.mahmudul.movieapp.network.ApiHelper
import com.mahmudul.movieapp.network.ApiHelperImpl
import com.mahmudul.movieapp.network.ApiService
import com.mahmudul.movieapp.ui.helper.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    private val READ_TIMEOUT = 130
    private val WRITE_TIMEOUT = 130
    private val CONNECTION_TIMEOUT = 110

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(
                Interceptor { chain ->
                    val original: Request = chain.request()
                    val requestBuilder: Request.Builder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                    val request: Request = requestBuilder.build()
                    chain.proceed(request)
                })
            .build()
    } else{
        OkHttpClient
            .Builder()
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)  // instance of api service

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper   //instance of api helper

}