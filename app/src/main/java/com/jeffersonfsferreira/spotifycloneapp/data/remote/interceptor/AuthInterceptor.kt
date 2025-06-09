package com.jeffersonfsferreira.spotifycloneapp.data.remote.interceptor

import android.util.Log
import com.jeffersonfsferreira.spotifycloneapp.data.local.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = tokenManager.getAccessToken()

        val requestBuilder = originalRequest.newBuilder()
        if(accessToken != null) {
            requestBuilder.header("Authorization", "Bearer $accessToken")
            Log.d("AuthInterceptor", requestBuilder.toString())
        }

        Log.d("AuthInterceptor", "REQUEST BUILDER DE FORA: ${requestBuilder.toString()}")

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}