package com.fappslab.tmdbcompose.core.presentaion.component.preview

import com.fappslab.tmdbcompose.core.domain.model.Detail
import com.fappslab.tmdbcompose.core.domain.model.Movie

fun detailDataPreview(): Detail {
    return Detail(
        id = 1,
        title = "Avatar: The Way of Water",
        genres = listOf("Science Fiction", "Adventure", "Action"),
        overview = "Set more than a decade after the events of the first film, learn the story of the Sully family (Jake, Neytiri, and their kids), the trouble that follows them, the lengths they go to keep each other safe, the battles they fight to stay alive, and the tragedies they endure.",
        imageUrl = "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg",
        releaseDate = "2022-12-14",
        voteAverage = 7.65,
        duration = 192,
        voteCount = 9977
    )
}

fun movieDataPreview(id: Int = 1) = Movie(
    id = id,
    title = "Avatar: The Way of Water",
    imageUrl = "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg",
    voteAverage = 7.65,
)

fun moviesDataPreview(): List<Movie> {
    return listOf(
        movieDataPreview(id = 1),
        movieDataPreview(id = 2),
        movieDataPreview(id = 3),
    )
}
