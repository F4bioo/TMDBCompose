package com.fappslab.features.search.data.api

import com.fappslab.core.data.remote.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface SearchService {

    @GET("search/multi")
    suspend fun getSearchMovie(
        @Query("page") page: Int,
        @Query("query") query: String
    ): MovieResponse
}
