package com.fappslab.tmdbcompose.core.data.network

interface HttpClient {
    fun <T> create(clazz: Class<T>): T
}
