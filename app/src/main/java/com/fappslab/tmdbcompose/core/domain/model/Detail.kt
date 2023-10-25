package com.fappslab.tmdbcompose.core.domain.model

import com.fappslab.tmdbcompose.core.data.remote.model.extension.INIT_DOUBLE
import com.fappslab.tmdbcompose.core.data.remote.model.extension.INIT_INT
import com.fappslab.tmdbcompose.core.data.remote.model.extension.INIT_STRING

data class Detail(
    val id: Int = INIT_INT,
    val title: String = INIT_STRING,
    val overview: String = INIT_STRING,
    val imageUrl: String = INIT_STRING,
    val releaseDate: String = INIT_STRING,
    val duration: Int = INIT_INT,
    val voteCount: Int = INIT_INT,
    val voteAverage: Double = INIT_DOUBLE,
    val genres: List<String> = emptyList()
)
