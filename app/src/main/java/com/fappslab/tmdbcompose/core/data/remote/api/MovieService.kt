package com.fappslab.tmdbcompose.core.data.remote.api

import com.fappslab.tmdbcompose.core.data.remote.model.DetailResponse
import com.fappslab.tmdbcompose.core.data.remote.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int
    ): DetailResponse

    @GET("search/multi")
    suspend fun getSearchMovie(
        @Query("page") page: Int,
        @Query("query") query: String
    ): MovieResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") id: Int,
        @Query("page") page: Int
    ): MovieResponse
}
