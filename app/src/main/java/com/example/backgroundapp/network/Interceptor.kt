package com.example.backgroundapp.network

import com.example.backgroundapp.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class Interceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = Constants.API_KEY

        val request = originalRequest.newBuilder()
            .header("Authorization", "Client-ID $token")
            .build()
        val response = chain.proceed(request)

        return response
    }
}