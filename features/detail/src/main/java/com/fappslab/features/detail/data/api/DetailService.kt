package com.fappslab.features.detail.data.api

import com.fappslab.core.data.remote.model.MovieResponse
import com.fappslab.features.detail.data.model.DetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface DetailService {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int
    ): DetailResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") id: Int,
        @Query("page") page: Int
    ): MovieResponse
}
