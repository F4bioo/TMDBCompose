package com.fappslab.core.data.local.model.extension

import com.fappslab.core.data.local.model.MovieEntity
import com.fappslab.core.domain.model.Movie

fun List<MovieEntity>.toMovies(): List<Movie> {
    return map { it.toMovie() }
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        voteAverage = voteAverage,
        imageUrl = imageUrl
    )
}

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        voteAverage = voteAverage,
        imageUrl = imageUrl
    )
}
