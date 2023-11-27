package com.fappslab.features.favorite.data.repository

import androidx.annotation.VisibleForTesting
import com.fappslab.core.data.remote.model.MovieResponse
import com.fappslab.core.data.remote.model.extension.toMovies
import com.fappslab.libraries.arch.jsonhandle.readFromJSONToModel

internal const val MOVIE_SUCCESS_RESPONSE = "movie_success.json"

@VisibleForTesting
internal object EntityFactory {
    private val movieResponse = readFromJSONToModel<MovieResponse>(MOVIE_SUCCESS_RESPONSE)
    val movie = movieResponse.result.toMovies().first()
    val movies = movieResponse.result.toMovies()
}
