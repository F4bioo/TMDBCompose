package com.fappslab.features.detail.data.model.extension


import com.fappslab.core.data.remote.model.extension.toImageUrl
import com.fappslab.core.domain.model.Detail
import com.fappslab.core.domain.model.Movie
import com.fappslab.features.detail.data.model.DetailResponse
import com.fappslab.features.detail.data.model.DetailResponse.GenreResponse
import com.fappslab.libraries.arch.extension.orDash
import com.fappslab.libraries.arch.extension.orZero

internal fun DetailResponse?.toDetail(): Detail =
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

internal fun Detail.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        voteAverage = voteAverage,
        imageUrl = imageUrl
    )
}
