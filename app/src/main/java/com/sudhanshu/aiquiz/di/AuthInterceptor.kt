package com.sudhanshu.aiquiz.di

import com.sudhanshu.aiquiz.core.utils.APISecret
import com.sudhanshu.aiquiz.core.utils.AppConfigurationConstants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer "+ APISecret.API_KEY)
            .addHeader("Content-Type", "application/json")
            .build()

        return chain.proceed(request)
    }
}