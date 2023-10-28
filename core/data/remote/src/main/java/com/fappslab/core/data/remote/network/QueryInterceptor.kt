package com.fappslab.core.data.remote.network

import com.fappslab.tmdbcompose.core.data.remote.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY_PARAM = "api_key"
private const val LANGUAGE_PARAM = "language"
private const val LANGUAGE_VALUE = "en-US"

internal class QueryInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter(API_KEY_PARAM, BuildConfig.API_KEY)
            .addQueryParameter(LANGUAGE_PARAM, LANGUAGE_VALUE)
            .build()
        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
