package com.fappslab.core.data.remote.network

interface HttpClient {
    fun <T> create(clazz: Class<T>): T
}
