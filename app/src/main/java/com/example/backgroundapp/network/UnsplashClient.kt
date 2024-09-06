package com.example.backgroundapp.network

import com.example.backgroundapp.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UnsplashClient {

    val okHttpClient=OkHttpClient.Builder()
        .addInterceptor(Interceptor())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val unsplashService = retrofit.create(UnsplashService::class.java)
}