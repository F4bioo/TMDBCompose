package com.fappslab.tmdbcompose.core.data.remote.model.extension

import com.fappslab.tmdbcompose.BuildConfig
import com.fappslab.tmdbcompose.core.data.common.extension.orDash
import com.fappslab.tmdbcompose.core.data.common.extension.orZero
import com.fappslab.tmdbcompose.core.data.remote.model.DetailResponse
import com.fappslab.tmdbcompose.core.data.remote.model.DetailResponse.GenreResponse
import com.fappslab.tmdbcompose.core.data.remote.model.MovieResponse.ResultResponse
import com.fappslab.tmdbcompose.core.domain.model.Detail
import com.fappslab.tmdbcompose.core.domain.model.Movie

private fun String?.toImageUrl(): String =
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

fun DetailResponse?.toDetail(): Detail =
    Detail(
        id = this?.id.orZero(),
        title = this?.title.orDash(),
        genres = this?.genres.toGenres(),
        overview = this?.overview.orDash(),
        imageUrl = this?.backdropPath.toImageUrl(),
        releaseDate = this?.releaseDate.orDash(),
        voteAverage = this?.voteAverage.orZero(),
        voteCount = this?.voteCount.orZero(),
        duration = this?.runtime.orZero()
    )


private fun List<GenreResponse?>?.toGenres(): List<String> {
    return this?.map {
        it?.name.orDash()
    }.orEmpty()
}

fun Detail.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        voteAverage = voteAverage,
        imageUrl = imageUrl
    )
}
