package com.fappslab.core.data.remote.model.extension

import com.fappslab.core.data.remote.model.MovieResponse.ResultResponse
import com.fappslab.core.domain.model.Movie
import com.fappslab.libraries.arch.extension.orDash
import com.fappslab.libraries.arch.extension.orZero
import com.fappslab.tmdbcompose.core.data.remote.BuildConfig

fun String?.toImageUrl(): String =
    "${BuildConfig.BASE_URL_IMAGE}$this"

fun List<ResultResponse?>?.toMovies(): List<Movie> {
    return this?.map {
        it.toMovie()
    }.orEmpty()
}

private fun ResultResponse?.toMovie(): Movie {
    return Movie(
        id = this?.id.orZero(),
        title = this?.title.orDash(),
        voteAverage = this?.voteAverage.orZero(),
        imageUrl = this?.posterPath.toImageUrl()
    )
}
