package com.fappslab.tmdbcompose.core.data.remote.network

interface HttpClient {
    fun <T> create(clazz: Class<T>): T
}
