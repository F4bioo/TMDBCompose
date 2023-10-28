package com.fappslab.features.favorite.presentation.viewmodel

import com.fappslab.core.domain.model.Movie

internal data class FavoriteViewState(
    val isFavoriteChecked: Boolean = true,
    val shouldShowLoading: Boolean = false,
    val movies: List<Movie> = emptyList()
)
