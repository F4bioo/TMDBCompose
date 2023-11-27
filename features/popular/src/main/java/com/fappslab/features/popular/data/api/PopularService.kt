package com.fappslab.features.popular.data.api

import com.fappslab.core.data.remote.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface PopularService {

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("page") page: Int
    ): MovieResponse
}
